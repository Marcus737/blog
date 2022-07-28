package com.wei.blogservice.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.wei.blogservice.entity.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 凌雪
 *
 */
@Component
public class JwtUtil {

    public static long tokenExpiredTime;

    public static long refreshTokenExpiredTime;

    private static String serKey;

    public static JwtToken createJwtWithUsername(String username){
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("username", username);
        return new JwtToken(createJwt(map,tokenExpiredTime), createJwt(map, refreshTokenExpiredTime));
    }

    public static String createTokenWithUsername(String username){
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("username", username);
        return createJwt(map, tokenExpiredTime);
    }

    public static JWT parseToken(String token){
        return JWTUtil.parseToken(token);
    }


    /**
     * 返回该token的剩余存活时间，单位秒
     * @param token
     * @return
     */
    public static long getRemainingTime(String token){
        JWT jwt = JWTUtil.parseToken(token);
        return  (int)jwt.getPayload(RegisteredPayload.EXPIRES_AT) - DateUtil.current() / 1000;
    }

    /**
     * 创建JWT
     */
    public static String createJwt(Map<String, Object> claims, Long time) {
        return JWT.create()
                .addPayloads(claims)
                .setIssuedAt(DateUtil.date())
                .setNotBefore(DateUtil.date())
                .setExpiresAt(DateUtil.date(DateUtil.current() + time))
                .sign(JWTSignerUtil.hs256(serKey.getBytes()));
    }

    /**
     * 验证jwt
     */
    public static boolean verifyJwt(String token) {
        if (token == null) {
            return false;
        }
        JWT jwt = JWTUtil.parseToken(token);
        jwt.setSigner(JWTSignerUtil.hs256(serKey.getBytes()));
        return jwt.validate(0);
    }


    @Value(value = "${jwt.tokenExpiredTime}")
    public void setTokenExpiredTime(long tokenExpiredTime) {
        JwtUtil.tokenExpiredTime = tokenExpiredTime;
    }

    @Value(value = "${jwt.refreshTokenExpiredTime}")
    public void setRefreshTokenExpiredTime(long refreshTokenExpiredTime) {
        JwtUtil.refreshTokenExpiredTime = refreshTokenExpiredTime;
    }

    @Value(value = "${jwt.serkey}")
    public void setSerKey(String key) {
        JwtUtil.serKey = key;
    }
}