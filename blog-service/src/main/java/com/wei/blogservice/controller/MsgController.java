package com.wei.blogservice.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wei.blogservice.entity.Msg;
import com.wei.blogservice.entity.Response;
import com.wei.blogservice.service.MsgService;
import com.wei.blogservice.utils.ImgUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 凌雪
 */
@RestController
@RequestMapping("/msg")
public class MsgController {

    @Resource
    MsgService msgService;


    @GetMapping("/getMsgList")
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response getMsgList(@RequestParam(value = "pageNum",required = false ,defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", msgService.getTotal());
        List<Msg> list = msgService.getMsgList(pageNum, pageSize);
        map.put("datas", list);
        return Response.ok(map);
    }


    @PostMapping("/addMsg")
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response addMsg(@RequestBody Msg msg){
        if (BeanUtil.isEmpty(msg)) {
            return Response.failFromReq("文章不能为空");
        }
        msg.setAvatar(ImgUtil.getAvatar());
        msg.setCreatedAt(new Date());
        try {
            msgService.saveMsg(msg);
        }catch (DuplicateKeyException e){
            return Response.failFromReq("名字重复");
        }
        return Response.ok(msg);
    }

    @GetMapping("/delMsg")
    @PreAuthorize("@ps.hasAuthority('admin')")
    public Response delMsg(@RequestParam("msgId") Integer msgId){
        if (msgService.delMsg(msgId)) {
            return Response.ok("删除成功");
        }
        return Response.failFromReq("删除失败");
    }

}
