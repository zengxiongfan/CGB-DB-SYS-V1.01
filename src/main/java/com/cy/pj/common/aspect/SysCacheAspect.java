package com.cy.pj.common.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class SysCacheAspect {
	  @Around("@annotation(com.cy.pj.common.annotation.RequiredCache)")
	  public Object aroundCache(ProceedingJoinPoint jp)
	  throws Throwable{
		  log.info("get data from cache");
		  Object result=jp.proceed();
		  log.info("put data to cache");
		  return result;
	  }
}
