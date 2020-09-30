package com.cy.pj.sys.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

@RestController
@RequestMapping("/user/")
public class SysUserController {
     @Autowired
	 private SysUserService sysUserService;
     
     @RequestMapping("doUpdatePassword")
	 public JsonResult doUpdatePassword(
			 String pwd,
			 String newPwd,
			 String cfgPwd) {
		 sysUserService.updatePassword(pwd, newPwd, cfgPwd);
		 return new JsonResult("update ok");
	 }
     @RequestMapping("doLogin")
     public JsonResult doLogin(
    		 String username,
    		 String password,
    		 boolean isRememberMe) {
    	 //1.获取Subject对象(主体对象,负责提交用户信息)
    	 Subject subject=SecurityUtils.getSubject();
    	 //2.执行登录,提交用户信息
    	 //2.1封装用户信息
    	 UsernamePasswordToken token=
    	 new UsernamePasswordToken(username, password);
    	 //2.2提交token(提交给SecurityManager)
    	 if(isRememberMe)token.setRememberMe(true);
    	 subject.login(token);
    	 return new JsonResult("login ok");
     }
     @RequestMapping("doFindObjectById")
     public JsonResult doFindObjectById(Integer id) {
    	 return new JsonResult(sysUserService
    			 .findObjectById(id));
     }
     @RequestMapping("doUpdateObject")
     public JsonResult doUpdateObject(SysUser entity,Integer[]roleIds) {
    	 sysUserService.updateObject(entity, roleIds);
    	 return new JsonResult("update ok");
     }
     @RequestMapping("doSaveObject")
     public JsonResult doSaveObject(SysUser entity,Integer[]roleIds) {
         sysUserService.saveObject(entity, roleIds);
         return new JsonResult("save ok");
     }
     /**
            *  禁用启用
      * @param valid
      * @param id
      */
     @RequestMapping("doValidById")
     public JsonResult doValidById(
    		 Integer valid,Integer id) {
    	 SysUser user=(SysUser)
    	 SecurityUtils.getSubject().getPrincipal();
    	 sysUserService.validById(valid, id,user.getUsername());
    	 return new JsonResult("update ok");
     }
     @RequestMapping("doFindPageObjects")
     public JsonResult doFindPageObjects(
    		String username,Integer pageCurrent) {
    	 return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
     }
}
