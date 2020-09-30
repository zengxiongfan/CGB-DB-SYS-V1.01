package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;
@Mapper
public interface SysRoleDao {
	/**查询所有角色的id和name*/
	@Select("select id,name from sys_roles")
	List<CheckBox> findRoles();
	/**
	  * 更新角色自身信息
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);
	/**
	  *  基于角色id查询角色自身信息以及对应的关系数据菜单id
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	/**
	 * 持久化角色自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);
	
	/**
	  * 基于角色id删除自身信息
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_roles where id=#{id}")
	int deleteObject(Integer id);
	
	/**
	  *    统计角色总数
	 * @param name
	 * @return
	 */
	int getRowCount(@Param("name")String name);
	/**
	   * 查询当前页记录
	 * @param name
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysRole> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
}






