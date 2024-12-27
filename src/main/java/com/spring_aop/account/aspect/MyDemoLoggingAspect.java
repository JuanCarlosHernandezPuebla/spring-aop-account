package com.spring_aop.account.aspect;

import com.spring_aop.account.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    @Around("execution(* com.spring_aop.account.service.*.getFortune(..))")
    public Object aroundGetFortune(ProceedingJoinPoint theProceedingJointPoint) throws Throwable {
        String method = theProceedingJointPoint.getSignature().toShortString();
        System.out.println("\n========>>> Executing @Around on method: " + method);

        long begin = System.currentTimeMillis();

        Object result = null;

        try {
            result = theProceedingJointPoint.proceed();
        }
        catch(Exception exc) {
            System.out.println(exc.getMessage());
            throw exc;
        }

        long end = System.currentTimeMillis();

        long duration = end - begin;

        System.out.println("\n=======> Duration: " + duration / 1000.0 + " seconds");

        return result;
    }

    @After("execution(* com.spring_aop.account.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n========>>> Executing @After (finally) on method: " + method);
    }

    @AfterThrowing(
            pointcut = "execution(* com.spring_aop.account.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc")
    public void afterThrowingFindAccountsAdvice(
            JoinPoint theJoinPoint, Throwable theExc) {

        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n========>>> Executing @AfterThrowing on method: " + method);

        System.out.println("\n========>>> The exception is: " + theExc);
    }

    @AfterReturning(
            pointcut = "execution(* com.spring_aop.account.dao.AccountDAO.findAccounts(..))",
            returning = "result"
    )
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n========>>> Executing @AfterReturning on method: " + method);

        System.out.println("\n========>>> results is: " + result);

        convertAccountNamesToUpperCase(result);

        System.out.println("\n========>>> results is: " + result);

    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        for(Account tempAccount: result) {
            String theUpperName = tempAccount.getName().toUpperCase();
            tempAccount.setName(theUpperName);
        }
    }

    @Before("com.spring_aop.account.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
        System.out.println("\n=======>>> Executing @Before advice on method");

        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();

        System.out.println("Method: " + methodSignature);

        Object[] args = theJoinPoint.getArgs();

        for(Object tempArg: args) {
            System.out.println(tempArg);

            if(tempArg instanceof Account) {
                Account theAccount = (Account) tempArg;

                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());

            }
        }
    }
}
