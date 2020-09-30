package com.cy.pj.common.util;
import org.apache.shiro.SecurityUtils;
import com.cy.pj.sys.entity.SysUser;
public class ShiroUtils {
	 /**获取登录用户*/
	 public static String getUsername() {
    	 SysUser user=(SysUser)
    	 SecurityUtils.getSubject().getPrincipal();
    	 return user.getUsername();
	 }
}





