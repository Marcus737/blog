package com.wei.blogservice.mappers;

import com.wei.blogservice.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 凌雪
 */
public interface ArticleMapper {

    /**
     * 3242df
     * @param start
     * @param size
     * @return
     */
    List<Article> getArticleList(@Param("start") Integer start, @Param("size") Integer size);


    int getTotalNum();

    Article getArticleDetail(@Param("id") Integer id);

    void likeArticle(@Param("id") Integer id, @Param("userId") Integer userId);

    void addLikeNum(@Param("id") Integer id);

    void saveArticle(@Param("art") Article article);

    void addVisitsNum(@Param("id") Integer id);

    int articleReadCount();

    Date getLastArticleDate();

    void updateArticle(@Param("art") Article article);

    void delArticle(@Param("id") Integer id);
}
