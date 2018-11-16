package com.primeton.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.primeton.demo.LiuyaweiDemoApplication;
import com.primeton.demo.dao.IOrgMapper;
import com.primeton.demo.model.Dept;
import com.primeton.demo.model.Emp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiuyaweiDemoApplication.class) 

public class DeptMapperTestCase {


	@Autowired
	private IOrgMapper deptMapper;

	/**
	 * 增
	 * 插入 yes
	 */
	@Test
	public void testInsertDept() 
	{
		Dept dept=new Dept("123","123","123","123");
		Object rows=deptMapper.insertDept(dept);
		Assert.assertEquals(1, rows);
	}

	/**
	 * 删
	 * 删除 yes 
	 */
	@Test
	public void testDeleteDept() 
	{
		Object rows=deptMapper.deleteDept("123");
		Assert.assertEquals(1, rows);
	}

	/**
	 * 
	 * 改
	 * 修改 yes
	 */
	@Test
	public void testUpdatetDept() 
	{

		Object rows=deptMapper.updateDept("123", "1","123");
		Assert.assertEquals(1, rows);
	}

	/**
	 * 查
	 * 查询测试部门员工 yes
	 */
	@Test
	public void testQureyEmpByDeptno() 
	{
		List<Emp> list=deptMapper.queryEmpByDeptno("3");
		for(Emp emp:list) {
			assertNotNull("该列为空异常",emp.getId());
		}

	}

	/**
	 * 查
	 * 查询测试部门子部门 yes
	 */
	@Test
	public void testQueryAllDeptsByLeader()
	{
		List<Dept> list=deptMapper.queryAllDeptsByLeader("3");
		for(Dept dept:list) {
			assertNotNull("该列为空异常",dept.getDeptno());

		}
	}

	/**
	 * 查
	 * 得到部门信息根据部门名称 yes 
	 */
	@Test
	public void testGetDeptByDeptName() 
	{
		Dept dept = deptMapper.getDeptByDeptName("研发1");
		assertNotNull("跟部门的信息为空",dept.getDeptName());


	}

	/**
	 * 查
	 * 根据部门号得到部门信息 yes
	 */
	@Test
	public void testGetDeptByDeptno() 
	{
		String deptno = "4";
		Dept dept = deptMapper.getDeptByDeptno(deptno);
		assertNotNull("跟部门的信息为空",dept.getDeptName());

	}


}


