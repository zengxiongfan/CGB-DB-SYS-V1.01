package com.cy.pj.common.aspect;
import java.lang.reflect.Method;
import java.util.Date;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.common.util.ShiroUtils;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
/**
 * @Aspect 用于描述切面类型
 * @Component 用于描述交给spring管理的对象
 */
@Slf4j
@Aspect
@Component
///@Order(1)
public class SysLogAspect {//代理对象调用此对象
	@Autowired
	private SysLogService sysLogService;
    /**
     *	 定义切入点(切入扩展功能的点):可能会对应很多类中的方法
     *   @Pointcut 是用于描述"切入点表达式"的一个注解
     * 	 常用的切入点表达式:例如bean表达式
     */
	//@Pointcut("bean(sysUserServiceImpl)")
	//@Pointcut("bean(*ServiceImpl)")
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
	public void doPointCut() {}
	/**@Around 描述的方法为一个环绕通知(Advice),在方法中可以
	 *     有选择的调用目标方法.
	 * 	由@Around注解描述的方法特点:
	 *  1)返回值类型为Object
	 *  2)参数类型ProceedingJoinPoint(连接点)
	 *  3)异常抛出:throwable
	 * */
	//@Around("bean(*ServiceImpl)")
	@Around("doPointCut()")
	public Object around(ProceedingJoinPoint jp)throws Throwable{
		try {
			long t1=System.currentTimeMillis();
			//执行下一个切面方法或目标方法
			//proceed方法的返回值就是目标方法的返回值
			Object result=jp.proceed();
			long t2=System.currentTimeMillis();
			log.info("method execute time :"+(t2-t1));
			//保存用户行为日志
			saveUserLog(jp,(t2-t1));
			return result;
		}catch(Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	/**保存用户行为日志信息*/
	private void saveUserLog(ProceedingJoinPoint jp,long time)throws Exception {
		//1.获取用户行为日志
		//1.1获取目标对象
		Class<?> targetCls=jp.getTarget().getClass();
		//1.2获取目标方法签名信息(包含方法名,参数列表等信息)
		//1.2.1获取方法(类名+方法名)
		MethodSignature ms=(MethodSignature)jp.getSignature();
		Method interfaceMethod=ms.getMethod();
		String methodName=interfaceMethod.getName();
		String clsMethodName=targetCls.getName()+"."+methodName;
		//1.2.2 获取方法参数(实际参数)
		System.out.println("clsMethodName="+clsMethodName);
		ObjectMapper om=new ObjectMapper();//jackson
		String params=om.writeValueAsString(jp.getArgs());//json
		//1.2.3获取注解RequiredLog
		Method targetMethod=
		targetCls.getMethod(methodName,ms.getParameterTypes());
		RequiredLog requiredLog=
		targetMethod.getAnnotation(RequiredLog.class);
		String operation=requiredLog.value();
		//2.封装用户行为日志
		 SysLog entity=new SysLog()
		.setUsername(ShiroUtils.getUsername())//登录使用的用户名
		.setOperation(operation)
		.setMethod(clsMethodName)//method=类全名+方法名
		.setParams(params)
		.setTime(time)
		.setIp(IPUtils.getIpAddr())
		.setCreatedTime(new Date());
		System.out.println("log.aspect:"+Thread.currentThread().getName());
		//3.存储用户行为日志
//		new Thread() {
//			public void run() {
//				sysLogService.saveObject(entity);//SysLog
//			};
//		}.start();
		sysLogService.saveObject(entity);//SysLog
	 
	}
}








