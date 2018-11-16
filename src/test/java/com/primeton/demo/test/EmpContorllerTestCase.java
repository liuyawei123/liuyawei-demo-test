package com.primeton.demo.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.primeton.demo.LiuyaweiDemoApplication;
import com.primeton.demo.controller.EmpController;
import com.primeton.demo.model.Emp;
import com.primeton.demo.model.ResponseResult;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiuyaweiDemoApplication.class) 

public class EmpContorllerTestCase {


	@Autowired
	private EmpController empController;

	/**
	 * 增
	 * 添加用户 yes
	 */
	@Test
	public void testCreateEmp() 
	{
		Emp emp=new Emp();
		emp.setEmpno("111");
		emp.setEmpName("王力宏");
		emp.setPassword("123");
		ResponseResult<Emp> r=empController.createEmp(emp);
		Assert.assertNotNull("创建用户信息异常", r);
	}

	/**
	 * 删
	 * 删除 yes
	 */
	@Test
	public void testRemoveEmp() 
	{
		ResponseResult<Void> rr=empController.removeEmp("王力宏");
		Assert.assertNotNull("0", rr.getState());
	}

	/**
	 * 改
	 * 修改 yes
	 */
	@Test
	public void testModifyEmp()
	{
		ResponseResult<Void> rr=empController.modifyEmp(38, "zhou", "123");
		Assert.assertNotNull("1", rr.getState());
	}

}


