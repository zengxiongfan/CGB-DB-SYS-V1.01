package com.cy.pj.common.config;
import java.util.LinkedHashMap;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class SpringShiroConfig {
     /**shiro的核心管理器对象(负责提供认证和授权操作)*/
	 @Bean 
	 public SecurityManager securityManager(
			 Realm realm,
			 CacheManager cacheManager) {
		 DefaultWebSecurityManager sm=
		 new DefaultWebSecurityManager();
		 sm.setRealm(realm);
		 sm.setCacheManager(cacheManager);
		 sm.setRememberMeManager(cookieRememberMeManager());
		 sm.setSessionManager(sessionManager());
		 return sm;
	 }
	 /**负责创建过滤器工厂,通过此工厂创建过滤器*/
	 @Bean("shiroFilterFactory")
	 public ShiroFilterFactoryBean newShirofFactoryBean(
			SecurityManager securityManager) {
		 ShiroFilterFactoryBean fBean=
		 new ShiroFilterFactoryBean();
		 fBean.setSecurityManager(securityManager);
		 fBean.setLoginUrl("/doLoginUI");
		 LinkedHashMap<String,String> filterMap=
			new LinkedHashMap<>();
		 filterMap.put("/bower_components/**", "anon");
		 filterMap.put("/build/**", "anon");//anon表示允许匿名访问
		 filterMap.put("/dist/**", "anon");
		 filterMap.put("/plugins/**", "anon");
		 filterMap.put("/user/doLogin","anon");
		 filterMap.put("/doLogout","logout");
		 //filterMap.put("/**", "user");//authc表示要认证才可访问
		 filterMap.put("/**", "anon");//authc表示要认证才可访问,暂时去掉认证 20200929zengxf
		 fBean.setFilterChainDefinitionMap(filterMap);
		 return fBean;
	 }
	 @Bean
	 public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		 return new LifecycleBeanPostProcessor();
	 }
	 /**
	  *	基于此对象扫描Spring容器中所有Advisor对象,并借助底层
	  * API为这些对象创建代理对象
	  * @return
	  */
	 @DependsOn("lifecycleBeanPostProcessor")
	 @Bean
	 public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		 return new DefaultAdvisorAutoProxyCreator ();
	 }
	 /**
	  * 	此对象实现了Spring中的 Advisor(顾问)接口,此对象
	  *	中要提供切入点.关联通知(Advice)对象进行功能扩展
	  * @return
	  */
	 @Bean
	 public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			 SecurityManager securityManager) {
		 AuthorizationAttributeSourceAdvisor advisor=
				new AuthorizationAttributeSourceAdvisor();
		 advisor.setSecurityManager(securityManager);
		 return advisor;
	 }
	 
	 //CacheManager
	 @Bean
	 public CacheManager newCacheManager(){
	 	 return new MemoryConstrainedCacheManager();
	 }
	 //RememberMe
	 public CookieRememberMeManager cookieRememberMeManager() {
		CookieRememberMeManager rManager=new CookieRememberMeManager();
		SimpleCookie cookie=new SimpleCookie("rememberMe");
		cookie.setMaxAge(60*60);
		rManager.setCookie(cookie);
		return rManager;
	 }
	 //session manager
	 public DefaultWebSessionManager sessionManager() {
		 DefaultWebSessionManager sManager=new DefaultWebSessionManager();
		 sManager.setGlobalSessionTimeout(60*60*1000);
		 return sManager;
	 }
}