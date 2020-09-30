package com.cy.pj.sys.dao;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;

@Mapper
public interface SysUserDao {
	@Update("update sys_users set password=#{password},salt=#{salt},modifiedTime=now() where id=#{id}")
	int updatePassword(
			@Param("password")String password,
			@Param("salt")String salt,
			@Param("id")Integer id);

	
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByUserName(String username);
	
	/**
	  * 基于用户id查询用户以及用户对应的部门信息
	 * @param id
	 * @return
	 */
	SysUserDeptVo findObjectById(Integer id);
	/**
	 * 将用户信息保存到数据库
	 * @param entity
	 * @return
	 */
	int updateObject(SysUser entity);
	/**
	  * 将用户信息保存到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
	 /**
	  * 禁用启用
	  * @param valid 状态
	  * @param id 用户id
	  * @param modifiedUser 修改用户
	  * @return 影响的行数
	  */
	 @Update("update sys_users set valid=#{valid},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	 int validById(
			 @Param("valid")Integer valid,
			 @Param("id")Integer id,
			 @Param("modifiedUser")String modifiedUser);
	 
	 int getRowCount(@Param("username")String username);
	 List<SysUserDeptVo> findPageObjects(
			@Param("username") String username,
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);
	 
}
