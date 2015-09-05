package com.learing.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * <pre>
 * Spring AOP - Pointcut pattern
 * private, protected methods would not support in predicate part
 * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)
 *             throws-pattern?)
 * </pre>
 * 
 * @author sriram
 *
 */
@Aspect
public class ApplicationLoggerAspect {

    @Pointcut("within(com.xyz.someapp.web..*)")
    public void inWebLayer() {
    }

    @Pointcut("execution(* *(..))")
    public void anyMatchPrdicate() {
    }

    @Pointcut("execution(public * *(..))")
    public void anyPublicMethodPrdicate() {
    }

    @Pointcut("execution(public * set*(..))")
    public void anyPublicSetterMethodPrdicate() {
    }

    @Pointcut("execution(public * get*(..))")
    public void anyPublicGetterMethodPrdicate() {
    }

    @Pointcut("within(com.learning.spring.*)")
    public void anyMethodWithinPackagePrdicate() {
    }

    @Pointcut("execution(* com.learning.spring.business.Calculator.*(..))")
    public void calculatorPrdicate() {
    }

    @Before("calculatorPrdicate()")
    public void printMethodEntryLoggerMessage(JoinPoint jp) {
	System.out.println(jp.getSignature().getName() + " method execution starts");
    }

    @After("calculatorPrdicate()")
    public void printMethodExitLoggerMessage(JoinPoint jp) {
	System.out.println(jp.getSignature().getName() + " method execution ends");
    }

}
