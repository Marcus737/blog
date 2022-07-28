package com.wei.blogservice.service;

import com.wei.blogservice.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author admin
 */
public interface ArticleService  {

    /**
     * 231
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Article> getArticleList(Integer pageNum, Integer pageSize);

    /**
     * 432e
     * @return
     */
    int getTotalNum();

    Article getArticleDetail(Integer id);

    void likeArticle(Integer id, Integer userId);

    void saveArticle(Article article);

    void addLikeNum(Integer id);

    void addVisitsNum(@Param("id") Integer id);

    int articleReadCount();

    Date getLastArticleDate();

    void updateArticle(Article article);

    void delArticle(Integer id);
}
