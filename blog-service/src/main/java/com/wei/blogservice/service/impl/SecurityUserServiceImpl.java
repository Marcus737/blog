package com.wei.blogservice.service.impl;

import com.wei.blogservice.entity.SecurityUser;
import com.wei.blogservice.entity.User;
import com.wei.blogservice.mappers.UserMapper;
import com.wei.blogservice.service.SecurityUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Resource
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("没有此用户：" + username);
        }
        return new SecurityUser(user.getUserId(), user.getUsername(), user.getPassword(), user.getRoles());
    }

}
