package com.wei.blogservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 凌雪
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleWord {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
    * 父id
    */
    private Integer floorId;

    /**
    * 评论详情
    */
    private String content;

    /**
    * 评论时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdAt;

    /**
    * 文章id
    */
    private Integer articleId;

    /**
    * 用户id
    */
    private Integer userId;

    /*用户信息*/
    private User user;
}