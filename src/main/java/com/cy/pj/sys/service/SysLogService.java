package com.cy.pj.sys.service;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
public interface SysLogService {
	/**
	 * 保存用户行为日志
	 * @param entity
	 * @return
	 */
	void saveObject(SysLog entity);
	//Future<Integer>  saveObject(SysLog entity);
	/**
	  * 基于id执行删除业务
	 * @param ids
	 * @return
	 */
	int deleteObjects(Integer...ids);
     /**
      * 获取当前页的分析信息
      * @param username 用户名
      * @param pageCurrent 当前页页码
      * @return 封装了当前页记录以及页码信息的对象
      */
	 PageObject<SysLog> findPageObjects(
			 String username,
			 Integer pageCurrent);
}
