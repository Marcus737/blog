package com.wei.blogservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author 凌雪
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
    * 作者用户id
    */
    private Integer userId;

    /**
    * 文章图片
    */
    private String imgUrl;

    /**
    * 文章配的音乐
    */
    private String musicName;

    /**
     * 音乐地址
     */
    private String musicUrl;

    /**
    * 文章标题
    */
    private String title;

    /**
    * 文章描述
    */
    private String description;

    /**
    * 访问次数
    */
    private Integer visitsNum;

    /**
    * 点赞次数
    */
    private Integer likeNum;

    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdAt;

    /**
    * 更新时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatedAt;

    /**
    * 文章内容
    */
    private String content;

    /**
    * classify_id
    */
    private Integer classifyId;

}