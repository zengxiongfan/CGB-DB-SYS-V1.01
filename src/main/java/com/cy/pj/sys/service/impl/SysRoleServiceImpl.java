package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;
import com.cy.pj.sys.vo.SysRoleMenuVo;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	//@Autowired
	private SysRoleDao sysRoleDao;
	//@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	//@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Autowired
	public SysRoleServiceImpl(SysRoleDao sysRoleDao, SysRoleMenuDao sysRoleMenuDao, SysUserRoleDao sysUserRoleDao) {
		super();
		this.sysRoleDao = sysRoleDao;
		this.sysRoleMenuDao = sysRoleMenuDao;
		this.sysUserRoleDao = sysUserRoleDao;
	}
	
	@Override
	public List<CheckBox> findRoles() {
		return sysRoleDao.findRoles();
	}
	
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		if(id==null||id<1)
		throw new IllegalArgumentException("id值无效");
		SysRoleMenuVo rm=
		sysRoleDao.findObjectById(id);
		if(rm==null)
		throw new ServiceException("记录可能已经不存在");
		return rm;
	}
	
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new ServiceException("必须为角色分配一个权限");
		//2.更新角色自身信息
		int rows=sysRoleDao.updateObject(entity);
		//3.更新角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回结果
		return rows;
	}
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new IllegalArgumentException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
		throw new ServiceException("必须为角色分配一个权限");
		//2.保存角色自身信息
		int rows=sysRoleDao.insertObject(entity);
		//3.保存角色菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回结果
		return rows;
	}
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值无效");
		//2.删除关系数据
		//2.1删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//2.2删除角色用户关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//3.删除自身信息
		int rows=sysRoleDao.deleteObject(id);
		//4.返回结果
		return rows;
	}
	
	@Override
	public PageObject<SysRole> findPageObjects(String name, 
			Integer pageCurrent) {
	    //1.验证参数的有效性
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("当前页码值无效");
		//2.获取总记录数并进行校验
		int rowCount=sysRoleDao.getRowCount(name);
		if(rowCount==0)
		throw new ServiceException("记录不存在");
		//3.获取当前页记录
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records=
		sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//4.对查询结果进行封装并返回
		return new PageObject<>(rowCount, records, pageCurrent, pageSize);
	}

}




