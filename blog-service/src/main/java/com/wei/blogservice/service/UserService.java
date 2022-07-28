package com.wei.blogservice.service;

import com.wei.blogservice.entity.LoginUser;
import com.wei.blogservice.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author 凌雪
 */
public interface UserService {

    User getUserByUsername(String username);

    User getLoginUserByUsername(String username);

    Boolean isUsernameExist(String username);

    Boolean createUser(LoginUser loginUser);
}
