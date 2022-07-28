package com.wei.blogservice.service.impl;

import com.wei.blogservice.entity.LoginUser;
import com.wei.blogservice.entity.User;
import com.wei.blogservice.mappers.UserMapper;
import com.wei.blogservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;


    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public User getLoginUserByUsername(String username) {
        return userMapper.getLoginUserByUsername(username);
    }

    @Override
    public Boolean isUsernameExist(String username) {
        return userMapper.isUsernameExist(username) > 0;
    }

    @Override
    @Transactional
    public Boolean createUser(LoginUser loginUser) {
        if ((userMapper.createUser(loginUser) > 0)) {
            userMapper.addDefaultRoleToUser(loginUser.getId(), 2);
            return true;
        }
        return false;
    }
}
