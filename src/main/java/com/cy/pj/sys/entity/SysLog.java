package com.cy.pj.sys.entity;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * 1.POJO (Plain Ordinary Java Object-普通的JAVA对象)
 * 1)PO (Persisent Object-持久化对象)-->特点:与表中字段有一一对应关系
 * 2).....
 * 2.SysLog对象是一个PO对象(与sys_logs表有映射关系)
 * 1)从数据库查询的一行日志记录直接封装到SysLog对象
 * 2)同时也可以在内存中封装要写入到数据库中的日志信息 
 */
@Accessors(chain = true)
@Data  //告诉lombok,编译时在此类中自动生成set/get方法
public class SysLog implements Serializable{
	private static final long serialVersionUID = -8427441809753041210L;
	private Integer id;
	//用户名
	private String username;
	//用户操作
	private String operation;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长(毫秒)
	private Long time;
	//IP地址
	private String ip;
	//创建时间
	private Date createdTime;

}
