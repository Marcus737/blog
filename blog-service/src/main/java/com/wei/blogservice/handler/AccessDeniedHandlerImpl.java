package com.wei.blogservice.handler;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wei.blogservice.entity.Response;
import com.wei.blogservice.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 * //权限异常会来到这里
 */
@Component
@CrossOrigin(maxAge = 2592000)
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String s = JSONUtil.toJsonStr(Response.fail(Response.ResponseState.NO_ACCESS, "权限不足"));
        response.setStatus(200);
        ResponseUtil.renderJson(response, s);
    }
}
