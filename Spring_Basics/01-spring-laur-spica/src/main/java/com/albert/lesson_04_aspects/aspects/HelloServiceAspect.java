package com.albert.lesson_04_aspects.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HelloServiceAspect {

    @Before("execution(* com.albert.lesson_04_aspects.service.HelloService.hello(..))")
    public void before() {
        System.out.println("Before hello");
    }

    @After("execution(* com.albert.lesson_04_aspects.service.HelloService.hello(..))")
    public void after() {
        System.out.println("After hello");
    }

    @AfterReturning("execution(* com.albert.lesson_04_aspects.service.HelloService.hello(..))")
    public void afterReturn() {
        System.out.println("After Return hello");
    }

    @AfterThrowing("execution(* com.albert.lesson_04_aspects.service.HelloService.hello(..))")
    public void afterThrowing() {
        System.out.println("After Throwing hello");
    }

    @Around("execution(* com.albert.lesson_04_aspects.service.HelloService.hello(..))")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("Around hello");
        Object ret;
        try {
//            ret = pjp.proceed();
            ret = pjp.proceed(new Object[]{"Parkinson"});
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return ret;
    }
}
