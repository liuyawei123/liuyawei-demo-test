package com.primeton.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.primeton.demo.LiuyaweiDemoApplication;
import com.primeton.demo.model.Dept;
import com.primeton.demo.service.IOrgService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiuyaweiDemoApplication.class) 

public class DeptControllerTestCase {


	@Autowired
	private IOrgService deptService;

	/**
	 * 增
	 * 插入  yes
	 */
	@Test
	public void testCreateDept() 
	{

		Dept dept = new Dept();
		dept.setDeptno("10");
		dept.setDeptName("开发2222");
		dept.setLeader("3");
		Dept res = deptService.createDept(dept);
		assertNotNull("返回的对象为空",res.getDeptName());
	}

	/**
	 * 删
	 * 删除部门 yes
	 */
	@Test
	public void testRemoveDept() 
	{

		Object row = deptService.removeDept("10");
		Assert.assertEquals(1, row);
	}

	/**
	 * 改
	 * 根据部门号修改部门信息 yes
	 */
	@Test
	public void testModifyDept()
	{

		Object row = deptService.modifyDept("研发2", "shanghai", "41");
		Assert.assertEquals(1, row);

	}

	/**
	 * 查
	 * 根据部门名称插叙部门信息 yes
	 */
	@Test
	public void  testGetDeptByDeptName() 
	{

		Dept dept = deptService.getDeptByDeptName("研发1");
		Assert.assertEquals("研发1", dept.getDeptName());

	}

}


