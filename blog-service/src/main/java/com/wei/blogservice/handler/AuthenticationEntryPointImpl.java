package com.wei.blogservice.handler;

import cn.hutool.json.JSONUtil;
import com.wei.blogservice.entity.Response;
import com.wei.blogservice.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 * //登录认证出现异常会来到这里
 */
@Component
@CrossOrigin(maxAge = 2592000)
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String s = JSONUtil.toJsonStr(Response.fail(Response.ResponseState.UN_AUTHENTICATION, authException.getMessage()));
        response.setStatus(400);
        ResponseUtil.renderJson(response, s);
    }
}
