package com.zyfgoup.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;

/**
 * @Author Zyfgoup
 * @Date 2020/12/2 9:32
 * @Description
 *    @Aspect 注解表示该类作为一个切面
 */
@Aspect
@Component
public class LogAspect {
    ObjectMapper mapper = new ObjectMapper();

    Logger log = LoggerFactory.getLogger(LogAspect.class);


    /**
     * @Description 定义切点
     * public为作用域
     * *会任意返回值
     * 然后指定包或者类
     *
     * *为任意类或者方法
     * (..)任何参数
     */
    @Pointcut("execution(public * com.zyfgoup.controller.*.*(..))")
    public void BrokerAspect(){

    }


    /**
     * 连接点之前执行的通知
     */
    @Before("BrokerAspect()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("我是在--调用controller层方法前--调用的方法");
        System.out.println(joinPoint.getSignature().toLongString());
        log.info("----------------我是在--调用controller层方法前--调用的方法");
    }

    /**
     * 连接点之后的通知
     */
    @After("BrokerAspect()")
    public void doAfter(){
        System.out.println("我是在--调用controller方法后--调用的方法");
    }

    /**
     * 方法抛出异常后的通知
     */
    @AfterThrowing("BrokerAspect()")
    public void doThrowing(){
        System.out.println("我是在--调用controller方法后有异常的情况下--调用的方法");
    }

    /**
     * 环绕通知
     * 但是这个方法是阻塞 需要执行了才能打印后面的
     * 每个方法都可以使用JoinPoint作为形参
     */
    @Around("BrokerAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println(joinPoint.getSignature().getName());
            System.out.println(Modifier.toString(joinPoint.getSignature().getModifiers()));
            System.out.println(((MethodSignature)joinPoint.getSignature()).getReturnType().getName());
            System.out.println(joinPoint.getArgs()[0].getClass().getName()+":"+joinPoint.getArgs()[0]);
            System.out.println("-----------------");
            System.out.println("环绕通知方法前");
            Object res = joinPoint.proceed();
            System.out.println(JSONObject.toJSONString(res, SerializerFeature.WriteMapNullValue));
            System.out.println("环绕通知方法后");
            return res;
        }catch (Throwable e){
            System.out.println("环绕增强捕捉异常"+e.getMessage());
            return null;
        }
    }


    /**
     *  通知结果返回后执行的
     *  对比上面的环绕 这是异步的
     *  可以通过 before+AfterReturning来打印调用方法的签名 结果等
     */
    @AfterReturning(pointcut = "BrokerAspect()",returning = "res")
    public void doAfterReturning(JoinPoint joinPoint,Object res){
        System.out.println("获取方法的返回值："+JSONObject.toJSONString(res,SerializerFeature.WriteMapNullValue));
    }



}
