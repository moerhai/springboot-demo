package com.mohai.one.springbootaop.aspect;

import com.mohai.one.springbootaop.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 定义切面
 * @Aspect 使之成为切面类
 * @Component 把切面类加入到IoC容器中
 */
@Aspect
@Component
public class AopAspect {

    /**
     *  @description 定义切入点，切入点为标有注解@Log的所有函数
     * 通过@Pointcut注解声明使用的切点表达式
     */
    @Pointcut("@annotation(com.mohai.one.springbootaop.annotation.Log)")
    public void LogAspect(){

    }

    /**
     * @description  在连接点执行之前执行的通知
     */
    @Before("LogAspect()")
    public void doBeforeLog(){
        System.out.println("执行controller前置通知");
    }

    /**
     * 注意该方法需要返回值
     * @description  使用环绕通知
     */
    @Around("LogAspect()")
    public Object doAroundLog(ProceedingJoinPoint pjp) throws Throwable {
        try{
            System.out.println("开始执行controller环绕通知");
            Object obj = pjp.proceed();
            System.out.println("结束执行controller环绕通知");
            return obj;
        }catch(Throwable e){
            System.out.println("出现异常");
            throw e;
        }
    }

    /**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("LogAspect()")
    public void doAfterLog(){
        System.out.println("执行controller后置结束通知");
    }

    /**
     * @description  在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning("LogAspect()")
    public void doAfterReturnLog(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        String name = log.name();
        System.out.println(name);
        System.out.println("执行controller后置返回通知");
    }

    /**
     * @description  在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing(pointcut="LogAspect()", throwing="e")
    public void doAfterThrowingLog(JoinPoint joinPoint, Throwable e){
        System.out.println("=====异常通知开始=====");
        System.out.println("异常代码:" + e.getClass().getName());
        System.out.println("异常信息:" + e.getMessage());
    }



}
