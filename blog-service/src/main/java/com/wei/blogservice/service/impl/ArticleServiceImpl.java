package com.wei.blogservice.service.impl;

import com.wei.blogservice.entity.Article;
import com.wei.blogservice.mappers.ArticleMapper;
import com.wei.blogservice.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 凌雪
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Override
    public List<Article> getArticleList(Integer pageNum, Integer pageSize) {

        return articleMapper.getArticleList((pageNum - 1) * pageSize, pageSize);
    }

    @Override
    public int getTotalNum() {
        return articleMapper.getTotalNum();
    }

    @Override
    public Article getArticleDetail(Integer id) {
        articleMapper.addVisitsNum(id);
        return articleMapper.getArticleDetail(id);
    }

    @Override
    public void likeArticle(Integer id, Integer userId) {
        articleMapper.likeArticle(id, userId);
        addLikeNum(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveArticle(Article article) {
        articleMapper.saveArticle(article);
    }

    @Override
    public void addLikeNum(Integer id) {
        articleMapper.addLikeNum(id);
    }

    @Override
    public void addVisitsNum(Integer id) {
        articleMapper.addVisitsNum(id);
    }

    @Override
    public int articleReadCount() {
        return articleMapper.articleReadCount();
    }

    @Override
    public Date getLastArticleDate() {
        return articleMapper.getLastArticleDate();
    }

    @Override
    @Transactional
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public void delArticle(Integer id) {
        articleMapper.delArticle(id);
    }
}
