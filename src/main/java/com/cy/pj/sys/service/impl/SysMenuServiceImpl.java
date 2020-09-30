package com.cy.pj.sys.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.annotation.RequiredCache;
import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

import lombok.extern.slf4j.Slf4j;

@Transactional(isolation = Isolation.READ_COMMITTED
              ,rollbackFor = Throwable.class,
              timeout = 30)
@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService{
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public int updateObject(SysMenu entity) {
		//1.参数校验(自己实现)
		//2.将数据对象持久化到数据库
		int rows=sysMenuDao.updateObject(entity);
		log.info("update rows "+rows);
		//3.返回结果
		return rows;
	}
	@Override
	public int saveObject(SysMenu entity) {
		//1.参数校验(自己实现)
		//2.将数据对象持久化到数据库
		int rows=sysMenuDao.insertObject(entity);
		log.info("insert rows "+rows);
		
		//3.返回结果
		return rows;
	}
	@Transactional(readOnly = true)
	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
	
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值无效");
		//2.判定菜单是否有子菜单,假如有则不允许删除
		int childCount=sysMenuDao.getChildCount(id);
		if(childCount>0)
		throw new ServiceException("请先删除子元素");
		//3.删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//4.删除菜单自身信息
		int rows=sysMenuDao.deleteObject(id);
		if(rows>0)
		throw new ServiceException("记录可能已经不存在了");
		return rows;
	}
	
	@Transactional(readOnly = true)
	@RequiredLog("查询菜单")
	@RequiredCache
	@Override
	public List<Map<String, Object>> findObjects() {
		System.out.println("SysMenuServiceImpl.findObjects:"+Thread.currentThread().getName());
		List<Map<String,Object>> list=
		sysMenuDao.findObjects();
		if(list==null||list.size()==0) {
		log.info("菜单信息不存在");
		throw new ServiceException("记录不存在");
		}
		return list;
	}
}
