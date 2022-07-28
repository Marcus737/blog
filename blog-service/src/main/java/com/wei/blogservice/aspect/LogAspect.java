package com.wei.blogservice.aspect;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.wei.blogservice.annotations.LogController;
import com.wei.blogservice.entity.ControllerLog;
import com.wei.blogservice.entity.SecurityUser;
import com.wei.blogservice.queue.consumer.Consumer;
import com.wei.blogservice.queue.pool.LogExecutor;
import com.wei.blogservice.utils.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * @author 凌雪
 */
@Aspect
@Component
public class LogAspect {

    @Resource
    LogExecutor logExecutor;
    @Resource
    ApplicationContext context;


    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.wei.blogservice.annotations.LogController)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        long begin = System.currentTimeMillis();
        result = joinPoint.proceed();

        ControllerLog data = new ControllerLog();
        data.setType("INFO");
        data.setTime(System.currentTimeMillis() - begin);
        setRequestInfo(data);
        setUserInfo(data);

        log(joinPoint, data);

        return result;
    }

    /**
     * 配置异常通知
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        ControllerLog data = new ControllerLog();
        String trace = null;
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)){
            e.printStackTrace(pw);
            trace = sw.toString();
        } catch (IOException ignored) {
        }
        data.setType("ERROR");
        data.setDescription(trace);
        setRequestInfo(data);
        setUserInfo(data);

        log(joinPoint, data);
    }


    private void setUserInfo(ControllerLog log) {
        //获取当前登录用户信息
        log.setCreateDate(new Date());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.setUsername(authentication.getName());
    }

    private void setRequestInfo(ControllerLog log) {
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        UserAgent agent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        log.setBrowser(agent.getBrowser().getName());
        log.setRequestIp(request.getRemoteAddr());
        log.setMethod(request.getMethod());
        log.setUri(request.getRequestURI());
    }

    private LogController getAnnotation(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        return method.getAnnotation(LogController.class);
    }

    private void log(JoinPoint joinPoint, Object data) {
        LogController annotation = getAnnotation(joinPoint);
        Consumer bean = context.getBean(annotation.consumer());
        bean.setData(data);
        logExecutor.getThreadPoolExecutor().execute(bean);
    }

}
