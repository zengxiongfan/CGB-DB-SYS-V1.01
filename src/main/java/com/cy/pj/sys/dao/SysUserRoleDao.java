package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
  *   基于此接口操作sys_user_roles表
 * @author Administrator
 */
@Mapper
public interface SysUserRoleDao {


	@Select("select role_id from sys_user_roles where user_id=#{id}")
	List<Integer> findRoleIdsByUserId(Integer id);
	 /**
	    * 将用户和角色关系数据写入到数据库
	  * @param userId
	  * @param roleIds
	  * @return
	  */
	 int insertObjects(
			 @Param("userId")Integer userId,
			 @Param("roleIds")Integer[]roleIds);
	 /**
	  *  基于用户id删除角色和用户关系数据  
	  * @param id
	  * @return
	  */
	 @Delete("delete from sys_user_roles where user_id=#{id}")
	 int deleteObjectsByUserId(Integer id);
     /**
            *  基于角色id删除角色和用户关系数据  
      * @param id
      * @return
      */
	 @Delete("delete from sys_user_roles where role_id=#{id}")
	 int deleteObjectsByRoleId(Integer id);
}







