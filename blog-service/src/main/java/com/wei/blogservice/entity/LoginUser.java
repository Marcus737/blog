package com.wei.blogservice.entity;

import com.wei.blogservice.annotations.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author 凌雪
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUser {

    private Integer id;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    private String avatar;
}
