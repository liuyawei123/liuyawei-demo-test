package com.primeton.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.primeton.demo.model.Dept;
import com.primeton.demo.model.ResponseResult;
import com.primeton.demo.model.Emp;
import com.primeton.demo.service.IOrgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 用户入口
 * 犯了一个自己目前不知原由的问题 把
 * @author liuya
 *
 */
@RequestMapping("/api/depts")
@RestController
@Api(value = "组织机构管理api", tags = "组织机构管理接口")
@Transactional(rollbackFor=Exception.class)
public class OrgController {

	@Autowired
	private IOrgService deptService;

	/**
	 * 增
	 * 创建部门
	 * @param deptno 部门号
	 * @param deptName 部门名称
	 * @param loc  部门地址
	 * @param leader 上级领导部门
	 * @return 创建状态
	 */
	@ApiOperation(value = "创建组织部门", response =ResponseResult.class)
	@PostMapping()
	public ResponseResult<Void> createDept(
			@RequestParam("deptno")@ApiParam("部门号") String deptno,
			@RequestParam("deptName")@ApiParam("部门名称") String deptName,
			@RequestParam("loc") @ApiParam("部门地址")String loc,
			@RequestParam("leader")@ApiParam("上级部门") String leader)
	{

		Dept dept = new Dept(deptno,deptName,loc,leader);
		System.out.println("插入数据结果为："+dept);
		//调用业务层的实现注册功能
		deptService.createDept(dept);
		//封装返回结果
		ResponseResult<Void> rr = new ResponseResult<Void>(ResponseResult.SUCCESS_MESSAGE,"恭喜您创建成功");
		return rr;

	}

	/**
	 * 删
	 * 根据部门好删除部门 
	 * @param deptno部门编号
	 * @return 删除状态
	 * //@DeleteMapping(value="/{deptno}")//不知道为什怎么测试都不管用//@PathVariable("deptno") String deptno
	 */
	@ApiOperation(value = "删除组织部门", response =ResponseResult.class)
	@DeleteMapping(value="/{deptno}")
	public ResponseResult<Void> removeDept(
			@PathVariable("deptno") @ApiParam("部门号") String deptno)
	{

		deptService.removeDept(deptno);
		ResponseResult<Void>  rr = new ResponseResult<Void>(ResponseResult.SUCCESS_MESSAGE,"恭喜您删除成功");
		return rr;
	}

	/**
	 * 改
	 * 修改部门信息
	 * @param deptName 部门名称
	 * @param loc   部门地址
	 * @param deptno 部门编号
	 * @param session 判断用书是否登录
	 * @return 用户修改状态
	 */
	@ApiOperation(value = "修改组织信息", response =ResponseResult.class)
	@PutMapping()
	public ResponseResult<Void> modifyDept(
			@RequestParam("deptName")@ApiParam("部门名称") String deptName,
			@RequestParam("loc") @ApiParam("部门地址")String loc,
			@RequestParam("deptno")@ApiParam("部门号") String deptno)
	{

		deptService.modifyDept(deptName, loc, deptno);
		//如果修改返回值1则修改成功
		ResponseResult<Void> rr  = new ResponseResult<Void>(ResponseResult.SUCCESS_MESSAGE,"修改成功");
		return rr;
	}



	/**
	 * 查
	 * 查询组织起部门信息
	 * @param deptno 部门号
	 * @return   
	 */
	@ApiOperation(value = "查询组织部门员工信息", response =ResponseResult.class)
	@GetMapping("/{deptno}")
	public ResponseResult<List<Emp>> getUserByOrg(
			@PathVariable("deptno") @ApiParam("部门号") String deptno)
	{
		List<Emp> data=deptService.queryEmpByDeptno(deptno);
		ResponseResult<List<Emp>> rr= new ResponseResult<List<Emp>>();
		rr.setData(data);
		return rr;
	}

	/**
	 * 查
	 * 查询所有员工信息
	 * @return
	 */
	@ApiOperation(value = "查询所有员工信息", response =ResponseResult.class)
	@GetMapping("/all")
	public ResponseResult<List<Emp>> getAllUsers()
	{
		List<Emp> data=deptService.queryAllUsers();
		ResponseResult<List<Emp>> rr= new ResponseResult<List<Emp>>();
		rr.setData(data);
		return rr;
	}


}
