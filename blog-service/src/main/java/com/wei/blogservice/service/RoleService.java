package com.wei.blogservice.service;

import com.wei.blogservice.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRoleByUsername(String username);
}
