package com.wei.blogservice.mappers;

import com.wei.blogservice.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {

    List<Role> getRoleByUsername(@Param("username") String username);
}
