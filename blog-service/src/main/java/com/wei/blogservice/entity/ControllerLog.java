package com.wei.blogservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 凌雪
 */
@Data
@NoArgsConstructor
public class ControllerLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 信息类型
     */
    private String type;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * description
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 方法参数
     */
    private String params;

    /**
     * 请求的ip
     */
    private String requestIp;

    /**
     * 方法执行时间（ms）
     */
    private Long time;

    /**
     * 浏览器
     */
    private String browser;

    /**
    * 请求路径
    */
    private String uri;

    /**
     * 日志创建时间
     */
    private Date createDate;

}
