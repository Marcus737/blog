package com.wei.blogservice.service.impl;

import cn.hutool.core.text.AntPathMatcher;
import com.wei.blogservice.entity.Permission;
import com.wei.blogservice.entity.Role;
import com.wei.blogservice.mappers.PermissionMapper;
import com.wei.blogservice.service.PermissionService;
import com.wei.blogservice.service.RoleService;
import com.wei.blogservice.utils.RequestUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author 凌雪
 */
@Service("ps")
public class PermissionServiceImpl implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;

    @Resource
    RoleService roleService;

    @Override
    public boolean hasPermission(){
       return hasPermission(getUri());
    }

    @Override
    public boolean hasPermission(String uri) {
        boolean res = false;
        //anonymousUser
        String username = getUsername();
        List<Permission> list = permissionMapper.getPermissionByUsername(username);
        AntPathMatcher matcher = new AntPathMatcher();
        for (Permission permission : list) {
            if (matcher.match(permission.getPermissionName(), uri)){
                res = true;
                break;
            }
        }
        return res;
    }

    @Override
    public boolean hasAuthority(String roleName) {
        String username = getUsername();
        List<Role> roleList = roleService.getRoleByUsername(username);
        for (Role role : roleList) {
            if (role.getRoleName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private String getUri(){
        return RequestUtil.getHttpServletRequest().getRequestURI();
    }
}
