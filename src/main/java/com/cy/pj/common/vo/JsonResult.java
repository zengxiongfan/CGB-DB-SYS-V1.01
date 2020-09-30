package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * VO:
 * 借助此对象封装服务端的影响数据
 * 1)响应的状态码 (1表示正常数据,0表示异常数据)
 * 2)响应消息 (呈现给用户的消息,例如一个弹出框中的数据)
 * 3)响应数据 (要呈现的正常数据,例如日志记录信息)
 * 4)....
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult implements Serializable{
	private static final long serialVersionUID = 5415540679197546732L;
	/**状态码*/
    private int state=1;
    /**状态码对应的状态信息*/
    private String message="ok";
    /**正常数据*/
    private Object data;
    public JsonResult(String message) {
    	this.message=message;
    }
    public JsonResult(Object data) {
    	this.data=data;
    }
    /**封装异常数据*/
    public JsonResult(Throwable e) {
    	this.state=0;
    	this.message=e.getMessage();
    }
}











