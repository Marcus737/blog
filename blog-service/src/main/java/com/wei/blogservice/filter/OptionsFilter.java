package com.wei.blogservice.filter;

import com.wei.blogservice.utils.ResponseUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 */
@Component
public class OptionsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("options".equalsIgnoreCase(request.getMethod())){
            ResponseUtil.renderJson(response, "");
            return;
        }
        doFilter(request, response, filterChain);
    }
}
