package com.primeton.demo.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.primeton.demo.model.Emp;
import com.primeton.demo.model.ResponseResult;
import com.primeton.demo.service.IEmpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户控制器
 * @author liuya
 *
 */
@Api(value = "用户管理api", tags = "用户管理接口")
@RequestMapping("/api/users")
@RestController
public class EmpController{

	@Autowired
	IEmpService userService;

	/**
	 * 增
	 * 用户注册
	 * @param empno 员工号
	 * @param empName 员工姓名
	 * @param password 员工密码
	 * @param job  员工职位
	 * @param mgr  上级领导
	 * @param deptno 部门号
	 * @return 注册状态
	 */
	@PostMapping()
	@ApiOperation(value = "创建用户", response = ResponseResult.class)
	public ResponseResult<Emp> createEmp( @ApiParam("需要保存的用户信息")Emp emp) 
	{

		ResponseResult<Emp> response = null;
		//调用业务层的实现注册功能
		emp=userService.createEmp(emp);
		//封装返回结果
		response = new ResponseResult<Emp>(ResponseResult.SUCCESS_MESSAGE,"注册成功");
		response.setData(emp);
		return response;
	}

	/**
	 * 删
	 * 删除 根据用户名删除该用户
	 * @param empName
	 * @return
	 */
	@ApiOperation(value = "删除用户", response = ResponseResult.class)
	@DeleteMapping(value = "/{empName}")
	public ResponseResult<Void> removeEmp(//personDelete
			@PathVariable("empName")@ApiParam("员工姓名") String empName)
	{

		userService.removeEmp(empName);
		return new ResponseResult<Void>(ResponseResult.SUCCESS_MESSAGE,"删除成功");
	}

	/**
	 * 改
	 * 用根据用户id修改个人数据
	 * @param id  用户id
	 * @param empName  用户名
	 * @param password 用户密码
	 * @param session 获取绑定数据
	 * @return 状态信息
	 */
	@ApiOperation(value = "修改用户", response = ResponseResult.class)
	@PutMapping()
	public ResponseResult<Void> modifyEmp(//handleChangeInfo
			@RequestParam("id")@ApiParam("员工ID") Integer id,
			@RequestParam("empName")@ApiParam("员工姓名") String empName,
			@RequestParam("password")@ApiParam("员工密码") String password) 
	{

		// 调用业务层对象的方法
		Integer e=userService.modifyEmp(empName, password, id);
		ResponseResult<Void> rr = new ResponseResult<Void>(
				ResponseResult.SUCCESS_MESSAGE,
				"修改成功您的新的账号和密码是：username："+empName+"password:"+password);
		return rr;
	}

	/**
	 * 查
	 * 用户登录
	 * @param empName  用户名
	 * @param password  密码
	 * @param session 记录
	 * @return  记录登录状态
	 */
	@ApiOperation(value = "登录", response = ResponseResult.class)
	@PostMapping("/actions/login")
	public ResponseResult<Emp> login(
			@RequestParam("empName") @ApiParam(value = "用户名", required = true) String empName,
			@RequestParam("password") @ApiParam(value = "密码", required = true) String password,HttpSession session) 
	{

		// 执行登录
		session.setAttribute("EMPNAME",empName);
		Emp emp=userService.login(empName, password);
		ResponseResult<Emp> response = new ResponseResult<Emp>(ResponseResult.SUCCESS_MESSAGE,"恭喜你登录成功");
		response.setData(emp);
		return response;

	}

	/**
	 * 用户退出
	 *
	 * @param session 用户推出请求
	 */
	@DeleteMapping(value = "/actions/out")
	@ApiOperation(value = "用户退出")
	public ResponseResult<Void> Exit(HttpSession session, HttpServletResponse response)
	{

		ResponseResult<Void>  rr=null;
		if(session.getAttribute("EMPNAME")!=null) {
			session.invalidate();
			rr = new ResponseResult<Void>(ResponseResult.SUCCESS_MESSAGE,"恭喜你退出成功");
		}else {
			rr = new ResponseResult<Void>(0,"您已经退");
		}
		return rr;

	}


}
