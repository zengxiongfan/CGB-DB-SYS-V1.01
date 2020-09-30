package com.cy.pj.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.entity.SysRole;
/**
 * @author Administrator
 */
@Mapper
public interface SysLogDao {
	/**
	 * 将用户行为日志写入到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysLog entity);
	/**
	  * 基于id执行日志删除操作
	 * @param ids
	 * @return
	 */
	int deleteObjects(Integer...ids);
	
	/***
	 * 基于条件查询总记录数
	 * @param username 查询条件
	 * @return 总记录数
	 */
	int getRowCount(@Param("username")String username);
	/**
	 * 基于条件查询当前页要呈现的记录
	 * @param username 查询条件
	 * @param startIndex 当前页起始位置
	 * @param pageSize 页面大小(每页最多要呈现的记录数)
	 * @return 当前页面要呈现的记录
	 */
	List<SysLog> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
}






