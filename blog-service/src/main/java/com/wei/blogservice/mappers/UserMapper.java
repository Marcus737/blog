package com.wei.blogservice.mappers;

import com.wei.blogservice.entity.LoginUser;
import com.wei.blogservice.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 凌雪
 */
@Repository
public interface UserMapper {
    User getUserByUsername(@Param("username") String username);

    User getLoginUserByUsername(@Param("username")String username);

    Integer isUsernameExist(@Param("username")String username);

    Integer createUser(@Param("loginUser") LoginUser loginUser);

    void addDefaultRoleToUser(@Param("id") Integer id, @Param("roleId") Integer roleId);
}
