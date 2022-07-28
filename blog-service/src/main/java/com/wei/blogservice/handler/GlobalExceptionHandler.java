package com.wei.blogservice.handler;

import cn.hutool.crypto.CryptoException;
import com.wei.blogservice.entity.Response;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author admin
 */
@ControllerAdvice
@ResponseBody
@CrossOrigin(maxAge = 2592000)
public class GlobalExceptionHandler {

    //处理参数异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response validParam(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : errors) {
            sb.append(error.getDefaultMessage()).append("\n");
        }
        return Response.fail(Response.ResponseState.BAD_REQUEST, sb.toString());
    }


    @ExceptionHandler(CryptoException.class)
    public Response handleCryptoException(){
       return Response.fail(Response.ResponseState.BAD_REQUEST, "RSA解密失败,用户名或密码错误");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Response badCredentialsException(){
        return Response.fail(Response.ResponseState.BAD_REQUEST, "用户名或密码错误");
    }


    @ExceptionHandler(AccessDeniedException.class)
    public Response accessDeniedException(){
        return Response.fail(Response.ResponseState.NO_ACCESS, "没有权限");
    }


    @ExceptionHandler(Exception.class)
    public Response handleOtherException(Exception e){
        e.printStackTrace();
        return Response.fail("服务器异常");
    }

}
