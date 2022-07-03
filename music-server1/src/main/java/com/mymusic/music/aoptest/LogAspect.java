package com.mymusic.music.aoptest;

import com.mymusic.music.MusicServer1Application;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.util.Arrays;

/**
 * @ClassName AOPTest
 * @Description TODO
 * @Author 86183
 * @Date2022-01-0614:38
 * @Version 1.0
 **/
@Aspect
@EnableAspectJAutoProxy
//@Component
public class LogAspect {
    /**
     * 获取类的全限定类名：joinPoint.getTarget().getClass().getName()
     * 获取方法名：joinPoint.getSignature().getName()
     * 获取方法的参数名：joinPoint.getArgs()
     */
    //记录器
    public static final Logger LOGGER = LoggerFactory.getLogger(MusicServer1Application.class);
    //抽取公共的切入点表达式
    @Pointcut("execution(* com.mymusic.music.controller.StockController.decrease(..))")
    public void pointCut(){};
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        LOGGER.info("@Before:"+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"运行的方法的参数列表"+ Arrays.asList(args));
    }
    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        LOGGER.info("@After:"+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"方法结束");
    }

    //如果有多个参数,JoinPoint必须在第一个,returning指定返回值要给那个形参(和形参名对应)
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        LOGGER.warn("@AfterReturning:"+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"方法的返回值:"+result);
    }

    //throwing指定异常要给哪个形参(和形参名对应)
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint,Exception exception){
        LOGGER.warn("@AfterThrowing:方法名为"+joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName()+"抛出的异常信息为："+exception);
    }
}
