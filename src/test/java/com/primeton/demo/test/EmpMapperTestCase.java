package com.primeton.demo.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.primeton.demo.LiuyaweiDemoApplication;
import com.primeton.demo.dao.IEmpMapper;
import com.primeton.demo.model.Emp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LiuyaweiDemoApplication.class)
public class EmpMapperTestCase {

	@Autowired
	private IEmpMapper userMapper;

	/**
	 * 插 注册测试成功
	 */
	@Test
	public void testInsertEmp() {
		Emp emp = new Emp();
		emp.setEmpName("刘亚威");
		emp.setPassword("123");
		emp.setCreatedDate(new Date());
		Object rows = userMapper.insertEmp(emp);
		Assert.assertEquals(1, rows);
	}

	/**
	 * 删 删除用户成功
	 */
	@Test
	public void testDelete() {
		Object row = userMapper.deleteEmp("liu");
		Assert.assertEquals(0, row);
	}

	/**
	 * 改 更新成功
	 */
	@Test
	public void testUpdata() {
		Emp emp = userMapper.getEmpByEmpName("aaa");
		emp.setEmpName("aaa");
		emp.setPassword("123");
		Object row = userMapper.updateEmp(emp);
		Assert.assertEquals(1, row);
	}

	/**
	 * 查 查询根据用户名测试成功
	 */
	@Test
	public void testGetEmpName() {
		Emp emp = userMapper.getEmpByEmpName("aaa");
		assertNotNull(emp.getId());
	}

	/**
	 * 查 查询根据id成功
	 */
	@Test
	public void testGetEmpByEmpId() {
		Emp emp = userMapper.getEmpByEmpId(3);
		assertNull(emp.getId());
	}
}
