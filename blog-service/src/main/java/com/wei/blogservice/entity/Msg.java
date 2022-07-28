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
@NoArgsConstructor
@AllArgsConstructor
public class Msg implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    /**
    * 评论内容
    */
    private String content;

    /**
    * 用户名
    */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
    * 创建时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

}