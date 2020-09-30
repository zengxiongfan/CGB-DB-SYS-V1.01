package com.cy.pj.sys.service;

import java.util.Map;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

public interface SysUserService {
	int updatePassword(String password,
	           String newPassword,
	           String cfgPassword);

	
	Map<String,Object> findObjectById(Integer id);
	
	/**更新用户以及用户和角色关系数据*/
	int updateObject(SysUser entity,Integer[]roleIds);
	/**保存用户以及用户和角色关系数据*/
	int saveObject(SysUser entity,Integer[]roleIds);
	/**
	 * 禁用启用用户信息
	 * @param valid
	 * @param id
	 * @param modifiedUser
	 * @return
	 */
	int validById(Integer valid,Integer id,String modifiedUser);
	 /**
	     * 分页查询用户信息
	  * @param username 查询条件
	  * @param pageCurrent 当前页码
	  * @return 封装了查询结果的对象
	  */
	 PageObject<SysUserDeptVo> findPageObjects(
			 String username,
			 Integer pageCurrent);
}





