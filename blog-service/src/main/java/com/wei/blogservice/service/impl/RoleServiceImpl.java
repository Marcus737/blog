package com.wei.blogservice.service.impl;

import com.wei.blogservice.entity.Role;
import com.wei.blogservice.mappers.RoleMapper;
import com.wei.blogservice.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleMapper roleMapper;

    @Override
    public List<Role> getRoleByUsername(String username) {
        return roleMapper.getRoleByUsername(username);
    }
}
