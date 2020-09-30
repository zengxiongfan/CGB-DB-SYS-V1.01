package com.cy.pj.sys.service;
import java.util.List;
import java.util.Map;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
/**
 * 此业务接口定义操作菜单数据的规范
 * @author Administrator
 */
public interface SysMenuService {
	
	/**
	 * 将菜单对象保存到数据库
	 * @param entity
	 * @return
	 */
	int updateObject(SysMenu entity);
	 /**
	  * 将菜单对象保存到数据库
	  * @param entity
	  * @return
	  */
	 int saveObject(SysMenu entity);
	
	 /**
	  * 查询所有菜单的id,name,parentId
	  * @return
	  */
	 List<Node> findZtreeMenuNodes();
	 /**
	  * 删除菜单对象
	  * @param id
	  * @return
	  */
	  int deleteObject(Integer id);
	  /**
	   * 查询所有菜单信息
	   */
	  List<Map<String,Object>> findObjects();
}
