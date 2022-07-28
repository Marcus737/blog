package com.wei.blogservice.service;

import com.wei.blogservice.entity.ArticleWord;
import com.wei.blogservice.entity.Msg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleWordService {
    void saveArticleWord(ArticleWord articleWord);

    List<ArticleWord> getArticleWordList(Integer articleId, Integer pageNum, Integer pageSize);

    int getTotal(Integer articleId);

    int getTotal();


    List<ArticleWord> getArticleWordList(Integer pageNum, Integer pageSize);

    void delArticleWord(Integer id);
}
