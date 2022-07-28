package com.wei.blogservice.filter;

import com.wei.blogservice.entity.JwtToken;
import com.wei.blogservice.entity.User;
import com.wei.blogservice.service.PermissionService;
import com.wei.blogservice.service.UserService;
import com.wei.blogservice.utils.JwtUtil;
import com.wei.blogservice.utils.RedisUtil;
import com.wei.blogservice.utils.ResponseUtil;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从header中拿到token，解析设置上下文
 * @author 凌雪
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION = "authorization";
    private static final String REFRESH_TOKEN ="refresh";
    private static final String PREFIX ="bearer ";
    @Resource
    UserService userService;

    @Resource
    RedisUtil redisUtil;

    @Resource
    PermissionService permissionService;

    /**
     * 验证token
     * 在SecurityContxtHolder设置认证信息
     * 不需要验证的直接放行
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (permissionService.hasPermission(request.getRequestURI())) {
            doFilter(request, response, filterChain);
            return;
        }

        String authenticationToken = request.getHeader(AUTHENTICATION);
        String refreshToken = request.getHeader(REFRESH_TOKEN);
        if (!StringUtils.hasText(authenticationToken) || !authenticationToken.startsWith(PREFIX)) {
            throw new InsufficientAuthenticationException("未携带有效token");
        }
        //bearer token的形式
        String token = authenticationToken.substring(PREFIX.length());

        String username = (String) JwtUtil.parseToken(token).getPayload("username");
        JwtToken jwtToken = redisUtil.getObject("security:" + username, JwtToken.class);
        //redis不可能为空，为空说明没有登录
        if (jwtToken == null) {
            throw new InsufficientAuthenticationException("登录已过期，请重新登录");
        }

        //验证token是否过期
        boolean b1 = JwtUtil.verifyJwt(token), b2 = JwtUtil.verifyJwt(refreshToken);
        if (!b1 && !b2) {
            throw new InsufficientAuthenticationException("登录已过期，请重新登录");
        }
        if (!b1 && b2){
            //若redis的token没过期，返回redis的token, 防止重复申请多个token
            if (JwtUtil.verifyJwt(jwtToken.getToken())){
                token = jwtToken.getToken();
            }else{
                //token过期但refreshToken没有过期，自动续签
                token = JwtUtil.createTokenWithUsername(username);
                //在redis中更新
                jwtToken.setToken(token);
                redisUtil.update("security:" + username, jwtToken, JwtToken.class);
            }
            response.setHeader("newToken", token);
        }

        //正常
        User user = userService.getLoginUserByUsername(username);

        //设置认证信息,只需用户名即可
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        doFilter(request, response, filterChain);
    }
}
