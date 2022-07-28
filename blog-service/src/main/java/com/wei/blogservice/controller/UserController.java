package com.wei.blogservice.controller;

import cn.hutool.core.img.Img;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wei.blogservice.entity.LoginUser;
import com.wei.blogservice.entity.Response;
import com.wei.blogservice.entity.User;
import com.wei.blogservice.service.ArticleService;
import com.wei.blogservice.service.MsgService;
import com.wei.blogservice.service.UserService;
import com.wei.blogservice.utils.ImgUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * @author 凌雪
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    PasswordEncoder encoder;

    @Resource
    RSA rsa;

    @Resource
    ArticleService articleService;

    @Resource
    MsgService msgService;

    @Value("${BCrypt.salt}")
    String salt;

    /**
     * 前端传过来的密码是经过base64加密过后的
     */
    @PostMapping("/createUser")
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response createUser(@Valid @RequestBody LoginUser user){

        if (userService.isUsernameExist(user.getUsername())){
            return Response.fail(Response.ResponseState.BAD_REQUEST, "用户名已存在");
        }

        //私钥解密
        byte[] decrypt = rsa.decrypt(user.getPassword(), KeyType.PrivateKey);
        user.setPassword(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        //byc加密明文密码
        user.setPassword(encoder.encode(user.getPassword() + salt));
        //设置用户头像
        user.setAvatar(ImgUtil.getAvatar());
        if (userService.createUser(user)) {
            return Response.ok("创建用户成功");
        }
        return Response.fail("创建用户失败！");
    }

    @GetMapping("/whoami")
    @PreAuthorize("@ps.hasAuthority('account')")
    public Response getUserByUsername(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null == auth) {
            return Response.fail("未登录");
        }
        User user = userService.getUserByUsername(auth.getName());
        user.setPassword(null);
        return Response.ok(user);
    }

    @GetMapping("/getHomeInfo")
    @PreAuthorize("@ps.hasAuthority('admin')")
    public Response getHomeInfo(){
        //articleCount
        //articleDiff
        //wordCount
        //访问人数
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("articleCount", articleService.getTotalNum());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        map.put("articleDiff", sdf.format(articleService.getLastArticleDate()));
        map.put("wordCount", msgService.getTotal());
        map.put("articleReadCount", articleService.articleReadCount());
        return Response.ok(map);
    }

    @GetMapping("/getUserByUsername/{username}")
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response getUserByUsername(@PathVariable("username") @NotBlank(message = "用户名不能为空") String username){
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return Response.ok(user);
        }
        return Response.fail("不存在该用户");
    }
}
