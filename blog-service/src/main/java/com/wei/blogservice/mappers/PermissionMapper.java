package com.wei.blogservice.mappers;

import com.wei.blogservice.entity.Permission;
import com.wei.blogservice.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 */
public interface PermissionMapper {

    /**
     * 12
     * @param username
     * @return
     */
    List<Permission> getPermissionByUsername(@Param("username") String username);

}
