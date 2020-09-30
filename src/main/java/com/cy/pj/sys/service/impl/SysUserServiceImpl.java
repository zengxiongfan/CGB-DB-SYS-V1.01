package com.cy.pj.sys.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.ShiroUtils;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public int updatePassword(String password,
			String newPassword, 
			String cfgPassword) {
        //1.参数校验
		if(StringUtils.isEmpty(password))
		throw new IllegalArgumentException("原密码不能为空");
		if(StringUtils.isEmpty(newPassword))
		throw new IllegalArgumentException("新密码不能为空");
		if(!newPassword.equals(cfgPassword))
		throw new IllegalArgumentException("两次密码输入必须一致");
		SysUser user=(SysUser)SecurityUtils.getSubject().getPrincipal();
        SimpleHash sh=new SimpleHash("MD5",
        		password, user.getSalt(), 1);
        String pwd=sh.toHex();
        if(!pwd.equals(user.getPassword()))
        throw new ServiceException("原密码不正确");
		//2.修改密码
        String salt=UUID.randomUUID().toString();
        sh=new SimpleHash("MD5",
        		newPassword, salt, 1);
        int rows=sysUserDao.updatePassword(sh.toHex(), salt,user.getId());
        //3.返回结果
		return rows;
	}
	
	@Override
	public Map<String,Object> findObjectById(Integer id) {
		//1.参数校验
		//2.查询
		SysUserDeptVo user=sysUserDao.findObjectById(id);
		if(user==null)
		throw new ServiceException("用户可能已经不存在");
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(id);
		//3.封装返回
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	@Override
	public int saveObject(SysUser entity, 
			Integer[] roleIds) {
	    long t1=System.currentTimeMillis();
		//1.验证参数有效性
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
		throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
		throw new IllegalArgumentException("密码不能为空");
		if(roleIds==null||roleIds.length==0)
		throw new IllegalArgumentException("必须为用户分配角色");
		//2.保存用户自身信息
		String password=entity.getPassword();
		String salt=UUID.randomUUID().toString();
		//借助shiro中的API对密码进行加密
		SimpleHash sh=new SimpleHash(
				"MD5",//algorithmName
				password,//source
				salt, 
				1);//hashIterations
		entity.setPassword(sh.toHex());
		entity.setSalt(salt);
   	    SysUser user=(SysUser)
   	    SecurityUtils.getSubject().getPrincipal();
		entity.setCreatedUser(user.getUsername());
		int rows=sysUserDao.insertObject(entity);
		//3.保存用户和角色关系数据
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		long t2=System.currentTimeMillis();
		log.info("execute time :"+(t2-t1));
		return rows;
	}
	@Override
	public int updateObject(SysUser entity, 
			Integer[] roleIds) {
		//1.验证参数有效性
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为用户分配角色");
		//2.保存用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.保存用户和角色关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer valid, Integer id, String modifiedUser) {
		//1.参数校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值无效");
		if(valid==null||(valid!=1&&valid!=0))
		throw new IllegalArgumentException("状态值不正确");
		//2.执行更新
		int rows=sysUserDao.validById(valid, id, modifiedUser);
		//3.返回结果
		if(rows==0)
	    throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("当前页码值无效");
		//2.查询总记录数,并进行校验
		int rowCount=sysUserDao.getRowCount(username);
		if(rowCount==0)
		throw new ServiceException("记录不存在");
		//3.查询当前页记录
		int pageSize=3;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records=
		sysUserDao.findPageObjects(username, startIndex, pageSize);
		//4.封装结果并返回
		return new PageObject<>(rowCount, records, pageCurrent, pageSize);
	}
}
