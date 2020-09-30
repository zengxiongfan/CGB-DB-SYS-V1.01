package com.cy.pj.sys.service.impl;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
/**
 * 业务层对象
 * 1)处理核心业务(分页查询日志记录)
 * 2)处理非核心业务(日志,权限,缓存,....)
 */
@Transactional
@Service
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogDao sysLogDao;
	//@Async 默认使用(SimpleAsyncTaskExecutor)
	@Async("asyncPoolExecutor")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void saveObject(SysLog entity) {
      System.out.println("SysLogServiceImpl.save:"+Thread.currentThread().getName());
	  sysLogDao.insertObject(entity);
	  try{Thread.sleep(5000);}catch(Exception e) {}
	}
//	@Async("asyncPoolExecutor")
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	@Override
//	public Future<Integer> saveObject(SysLog entity) {
//		System.out.println("SysLogServiceImpl.save:"+Thread.currentThread().getName());
//		int rows=sysLogDao.insertObject(entity);
//		try{Thread.sleep(5000);}catch(Exception e) {}
//	    return new AsyncResult<Integer>(rows);
//	}
	/**
	 * @RequiresPermissions 用于告诉底层系统访问此方法
	  *   需要什么样的权限,例如"sys:log:delete"
	 */
	@RequiresPermissions("sys:log:delete")
	@Override
	public int deleteObjects(Integer... ids) {
		//1.参数校验
		if(ids==null||ids.length==0)
		throw new IllegalArgumentException("必须先选中");
		//2.执行删除
		int rows=sysLogDao.deleteObjects(ids);
		//3.判定结果
		if(rows==0)
		throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		//1.验证当前页码值是否合法
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("当前页码值不正确");
		//2.基于用户名查询总记录数并进行校验
		int rowCount=sysLogDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("记录不存在");
		//3.查询当前页记录
		Integer pageSize=3;
		Integer startIndex=(pageCurrent-1)*pageSize;
		List<SysLog> records=
		sysLogDao.findPageObjects(username,
				startIndex, pageSize);
		//4.封装查询结果并返回
		return new PageObject<>(
		rowCount, records,pageCurrent, pageSize);
	}
}
