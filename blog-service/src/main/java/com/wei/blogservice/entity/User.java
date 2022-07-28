package com.wei.blogservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 凌雪
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String account;
    private String aboutMe;
    private Date birthday;
    private String avatar;
    private String motto;
    private Date createdAt;
    private List<Role> roles;
}
