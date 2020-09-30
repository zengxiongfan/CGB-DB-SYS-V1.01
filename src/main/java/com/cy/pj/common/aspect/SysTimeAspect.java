package com.cy.pj.common.aspect;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class SysTimeAspect {

	  //@Pointcut("within(com.cy.pj.sys.service..*)")
	  @Pointcut("bean(*ServiceImpl)")
	  public void doTime() {}
	  
	  @Before("doTime()")
	  public void doBefore(JoinPoint jp) {
		  log.info("time @Before");
	  }
	  @After("doTime()")
	  public void doAfter() {
		  log.info("time @After");
	  }
	  @AfterReturning("doTime()")
	  public void doAfterReturning() {
		  log.info("time @AfterReturning");
	  }
	  @AfterThrowing("doTime()")
	  public void doAfterThrowing() {
		  log.info("time @AfterThrowing");
	  }
	  
	  @Around("doTime()")
	  public Object doAround(ProceedingJoinPoint jp)
	  throws Throwable{
		  try {
		   log.info("time @Around before");
		   Object result=jp.proceed();
		   log.info("time @Around after");
		   return result;
		  }catch (Throwable e) {
		   log.info("time @Around exception");
		   throw e;
		  }
	  }
}








