package com.wei.blogservice.controller;

import com.wei.blogservice.entity.ArticleWord;
import com.wei.blogservice.entity.Response;
import com.wei.blogservice.service.ArticleWordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/articleWord")
public class ArticleWordController {

    @Resource
    ArticleWordService articleWordService;

    @PostMapping("/addArticleWord")
    @PreAuthorize("@ps.hasAuthority('account')")
    public Response addArticleWord(@RequestBody ArticleWord articleWord){
        articleWord.setCreatedAt(new Date());
        articleWordService.saveArticleWord(articleWord);
        return Response.ok("保存成功");
    }

    @GetMapping("/getArticleWordList")
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response getArticleWordList(@RequestParam("articleId") Integer articleId,
                                                @RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize){
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", articleWordService.getTotal(articleId));
        List<ArticleWord> list = articleWordService.getArticleWordList(articleId, pageNum, pageSize);
        map.put("datas", list);
        return Response.ok(map);
    }

    @GetMapping("/getArtWordList")
    @PreAuthorize("@ps.hasAuthority('admin')")
    public Response getArtWordList( @RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize){
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("total", articleWordService.getTotal());
        List<ArticleWord> list = articleWordService.getArticleWordList(pageNum, pageSize);
        map.put("datas", list);
        return Response.ok(map);
    }

    @GetMapping("/delArticleWord")
    @PreAuthorize("@ps.hasAuthority('admin')")
    public Response delArticleWord(@RequestParam("id")Integer id){
        articleWordService.delArticleWord(id);
        return Response.ok("删除成功");
    }


}
