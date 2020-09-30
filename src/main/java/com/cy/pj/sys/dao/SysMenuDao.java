package com.cy.pj.sys.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
/**
 * @Mapper 注解描述的接口用于告诉系统底层为此接口
 * 产生一个实现类的对象.
 */
@Mapper
public interface SysMenuDao {
	List<String> findPermissions(
			@Param("menuIds")
			Integer[] menuIds);
	/**
	 * 对菜单信息持久化,更新到数据库
	 * @param entity
	 */
	int updateObject(SysMenu entity);
	/**
	 * 对菜单信息持久化,写入到数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysMenu entity);
	
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
	/**
	 * 查询当前菜单是否有子菜单
	 * @param id
	 * @return 统计的记录
	 */
	@Select("select count(*) from sys_menus where parentId=#{id}")
	int getChildCount(Integer id);
	
	@Delete("delete from sys_menus where id=#{id}")
	int deleteObject(Integer id);
	
	
	List<Map<String,Object>> findObjects();
}
