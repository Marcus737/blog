package com.wei.blogservice.config;


import cn.hutool.crypto.asymmetric.RSA;
import com.wei.blogservice.filter.CorsFilter;
import com.wei.blogservice.filter.JwtAuthenticationTokenFilter;
import com.wei.blogservice.filter.OptionsFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author 凌雪
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    JwtAuthenticationTokenFilter jwtFilter;

    @Resource
    OptionsFilter optionsFilter;

    @Resource
    CorsFilter corsFilter;

    @Resource
    AccessDeniedHandler accessDeniedHandler;

    @Resource
    AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    PasswordEncoder encoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf功能，前后端分离
        http.csrf().disable();
        //不需要表单登录
        http.formLogin().disable();
        http.rememberMe().disable();
        http.logout().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        //除了登录和登出，其他操作都需要登录后进行
        http.authorizeRequests()
                .anyRequest()
                .permitAll();
        http.exceptionHandling()
                //权限不足时
                .accessDeniedHandler(accessDeniedHandler)
                //需要登录时
                .authenticationEntryPoint(authenticationEntryPoint);
        http.addFilterAfter(jwtFilter, ExceptionTranslationFilter.class);
        http.addFilterBefore(optionsFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(corsFilter, OptionsFilter.class);
    }

    /**
     * 因为在jwt filter过滤时，只有用户名和加密后的密码，而默认密码加密器需要明文密码与加密后的密码进行比较
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }


    @Bean
    public RSA getRSA(@Value("${rsa.privateKey}") String privateKey){
        return new RSA(privateKey, null);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
