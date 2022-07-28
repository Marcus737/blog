package com.wei.blogservice.controller;

import cn.hutool.core.bean.BeanUtil;
import com.wei.blogservice.entity.Article;
import com.wei.blogservice.entity.Response;
import com.wei.blogservice.service.ArticleService;
import com.wei.blogservice.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author admin
 */
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Resource
    ArticleService articleService;

    @Resource
    UserService userService;


    @GetMapping("/getArticleList")
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response getArticleList(@RequestParam(value = "pageNum",required = false ,defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", articleService.getTotalNum());
        List<Article> list = articleService.getArticleList(pageNum, pageSize);
        map.put("datas", list);
        return Response.ok(map);
    }

    @GetMapping("/getArticleDetail")
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response getArticleDetail(@RequestParam("id") Integer id){
        return Response.ok(articleService.getArticleDetail(id));
    }

    @GetMapping("/likeArticle")
    @PreAuthorize("@ps.hasAuthority('account')")
    public Response likeArticle(@RequestParam(value = "id") Integer id,
                                @RequestParam(value = "userId") Integer userId){
        try {
            articleService.likeArticle(id, userId);
        }catch (DuplicateKeyException e){
            return Response.fail("已经点过赞了");
        }
        return Response.ok("点赞成功");
    }

    @PostMapping("/addArticle")
    @PreAuthorize("@ps.hasAuthority('admin')")
    public Response addArticle(@RequestBody Article article){
        if (BeanUtil.isEmpty(article)) {
            return Response.failFromReq("文章不能为空");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = userService.getLoginUserByUsername(authentication.getName()).getUserId();
        article.setUserId(userId);
        article.setCreatedAt(new Date());
        article.setUpdatedAt(new Date());
        article.setVisitsNum(0);
        article.setLikeNum(0);
        articleService.saveArticle(article);
        return Response.ok("添加文章成功");
    }

    @PreAuthorize("@ps.hasAuthority('admin')")
    @PostMapping("/updateArticle")
    public Response updateArticle(@RequestBody Article article){
        if (BeanUtil.isEmpty(article)) {
            return Response.failFromReq("文章不能为空");
        }
        if (article.getId() == null){
            return Response.failFromReq("文章id不能为空");
        }
        article.setUpdatedAt(new Date());
        articleService.updateArticle(article);
        return Response.ok("更新文章成功");
    }

    @GetMapping("/delArticle")
    @PreAuthorize("@ps.hasAuthority('admin')")
    public Response delArticle(@RequestParam("id")Integer id){
        articleService.delArticle(id);
        return Response.ok("文章已删除");
    }
}
