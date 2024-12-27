package com.spring_aop.account.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {

    @Pointcut("execution(* com.spring_aop.account.dao.*.*(..))")
    public void forDaoPackage() {}

    @Pointcut("execution(* com.spring_aop.account.dao.*.get*(..))")
    public void getter() {}

    @Pointcut("execution(* com.spring_aop.account.dao.*.set*(..))")
    public void setter() {}

    @Pointcut("forDaoPackage() && !(getter() || setter())")
    public void forDaoPackageNoGetterSetter() {}
}
