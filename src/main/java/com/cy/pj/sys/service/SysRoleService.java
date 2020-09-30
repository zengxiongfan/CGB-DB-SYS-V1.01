package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
	List<CheckBox> findRoles();
	
	/**
	 *    基于角色id获取角色自身信息以及对应的关系数据菜单id
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	/**
	 * 更新角色信息以及对应的菜单关系数据
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int updateObject(SysRole entity,Integer[] menuIds);
	/**
	 * 保存角色信息以及对应的菜单关系数据
	 * @param entity
	 * @param menuIds
	 * @return
	 */
	int saveObject(SysRole entity,Integer[] menuIds);
	/**
	  * 基于角色id删除角色以及对应的关系数据
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
     /**
             * 分页查询角色信息
      * @param name
      * @param pageCurrent
      * @return
      */
	 PageObject<SysRole> findPageObjects(
			 String name,
			 Integer pageCurrent);
}






