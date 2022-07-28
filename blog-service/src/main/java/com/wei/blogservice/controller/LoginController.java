package com.wei.blogservice.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wei.blogservice.annotations.LogController;
import com.wei.blogservice.entity.JwtToken;
import com.wei.blogservice.entity.LoginUser;
import com.wei.blogservice.entity.Response;
import com.wei.blogservice.entity.User;
import com.wei.blogservice.service.UserService;
import com.wei.blogservice.utils.JwtUtil;
import com.wei.blogservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author 凌雪
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Resource
    UserService userService;

    @Resource
    AuthenticationManager manager;

    @Resource
    RSA rsa;

    @Resource
    RedisUtil redisUtil;

    @Value("${BCrypt.salt}")
    String salt;

    /**
     * 若redis有该用户的token和refreshtoken，
     * 则把这两个加入到redis， key为token
     * @return 12
     */
    @GetMapping("/logout")
    @LogController
    @PreAuthorize("@ps.hasAuthority('account')")
    public Response logout(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        redisUtil.del("security:" + username);
        return Response.ok("登出成功");
    }

    public static void main(String[] args) {
        String s1 = "whenyouregone.$23s*$5?s";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(s1));
    }

    /**
     * 前端传过来的密码是经过base64加密过后的
     * 防止用户多次登录返回多个token，每次登录先验证用户名和密码再去redis查询该用户是否有token，有则直接返回
     */
    @PostMapping("/login")
    @LogController
    @PreAuthorize("@ps.hasAuthority('unknown')")
    public Response login(@Valid @RequestBody LoginUser user, HttpServletResponse response){
        //验证用户名
        User dbUser = userService.getLoginUserByUsername(user.getUsername());
        if (dbUser == null){
            return Response.fail(Response.ResponseState.BAD_REQUEST, "用户名或密码错误");
        }
        //私钥解密
        byte[] decrypt = rsa.decrypt(user.getPassword(), KeyType.PrivateKey);
        user.setPassword(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8) + salt);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = manager.authenticate(token);
        if (authentication == null){
            return Response.fail(Response.ResponseState.BAD_REQUEST, "用户名或密码错误");
        }
        SecurityContextHolder.getContext().setAuthentication(token);

        //这里已经是登录验证成功的了
        return Response.ok(getAuthToken(user.getUsername()));
    }

    private JwtToken getAuthToken(String username){
        JwtToken jwtToken = redisUtil.getObject("security:" + username, JwtToken.class);
        if (jwtToken == null) {
            JwtToken res = JwtUtil.createJwtWithUsername(username);
            //存入redis, 设置过期时间为refreshToken的过期时间
            redisUtil.psetex("security:" + username, res, JwtUtil.refreshTokenExpiredTime);
            return res;
        }
        if (!JwtUtil.verifyJwt(jwtToken.getToken())){
            //token在redis过期了, 重新申请一个
            if (JwtUtil.verifyJwt(jwtToken.getRefreshToken())) {
                jwtToken.setToken(JwtUtil.createTokenWithUsername(username));
                //在redis中更新
                redisUtil.update("security:" + username, jwtToken, JwtToken.class);
            }
            //若两个都过期，在redis返回的json应为null，上面已经处理了
        }
        //这里就是两个都没过期，直接返回
        return jwtToken;
    }

}
