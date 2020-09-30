package com.cy.test.log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLogServiceTests {
	 @Autowired
     private SysLogService sysLogService;
	 @Test
	 public void testFindPageObjects() {
		 PageObject<SysLog> po=
		  sysLogService.findPageObjects("a",1);
		 System.out.println(po.getRowCount());
		 System.out.println(po.getPageCount());
		 System.out.println("list.size="+po.getRecords().size());
	 } 
}
