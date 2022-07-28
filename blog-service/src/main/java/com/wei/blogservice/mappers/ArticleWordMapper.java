package com.wei.blogservice.mappers;

import com.wei.blogservice.entity.ArticleWord;
import com.wei.blogservice.entity.Msg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleWordMapper {

    void saveArticleWord(@Param("aw") ArticleWord articleWord);

    List<ArticleWord> getArticleWordList(@Param("articleId")Integer articleId, @Param("start") Integer start, @Param("size") Integer size);

    int getTotal(@Param("articleId") Integer articleId);


    int getAllTotal();

    List<ArticleWord> getAllArticleWordList(@Param("start") Integer start, @Param("size")Integer size);

    void delArticleWord(@Param("id") Integer id);
}
