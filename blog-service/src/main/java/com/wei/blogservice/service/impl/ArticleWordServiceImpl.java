package com.wei.blogservice.service.impl;

import com.wei.blogservice.entity.ArticleWord;
import com.wei.blogservice.mappers.ArticleWordMapper;
import com.wei.blogservice.service.ArticleWordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleWordServiceImpl implements ArticleWordService {

    @Resource
    ArticleWordMapper articleWordMapper;

    @Override
    public void saveArticleWord(ArticleWord articleWord) {
        articleWordMapper.saveArticleWord(articleWord);
    }

    @Override
    public List<ArticleWord> getArticleWordList(Integer articleId, Integer pageNum, Integer pageSize) {
        return articleWordMapper.getArticleWordList(articleId, (pageNum-1)*pageSize, pageSize);
    }

    @Override
    public int getTotal(Integer articleId) {
        return articleWordMapper.getTotal(articleId);
    }

    @Override
    public int getTotal() {
        return articleWordMapper.getAllTotal();
    }

    @Override
    public List<ArticleWord> getArticleWordList(Integer pageNum, Integer pageSize) {
        return articleWordMapper.getAllArticleWordList((pageNum-1)*pageSize, pageSize);
    }

    @Override
    public void delArticleWord(Integer id) {
        articleWordMapper.delArticleWord(id);
    }

}
