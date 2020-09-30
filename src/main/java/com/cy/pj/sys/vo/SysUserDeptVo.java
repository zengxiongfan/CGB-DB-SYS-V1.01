package com.cy.pj.sys.vo;

import java.io.Serializable;
import java.util.Date;

import com.cy.pj.sys.entity.SysDept;
import lombok.Data;
/***
  *  借助此对象封装从数据库查询到的用户及用户对应的部门信息
 * @author Administrator
 *
 */
@Data
public class SysUserDeptVo implements Serializable{
	private static final long serialVersionUID = 1138534420870847335L;
	private Integer id;
	private String username;
	private String password;//md5
	/**盐值(加密盐-辅助加密,保证密码更加安全)*/
	private String salt;
	private String email;
	private String mobile;
	/**用户状态:1表示启用,0表示禁用*/
	private Integer valid=1;
	/**用户所在部门的部门信息*/
	private SysDept sysDept; //private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;

}
