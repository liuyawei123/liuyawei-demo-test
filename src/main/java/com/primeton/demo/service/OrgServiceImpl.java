package com.primeton.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.primeton.demo.dao.IOrgMapper;
import com.primeton.demo.excption.DemoException;
import com.primeton.demo.model.Dept;
import com.primeton.demo.model.ResponseResult;
import com.primeton.demo.model.Emp;

/**
 * 组织接口
 * @author liuya
 *
 */
@Service("deptService")
@Transactional(rollbackFor=Exception.class)
public class OrgServiceImpl implements IOrgService{

	@Autowired
	IOrgMapper deptMapper;


	/**
	 * 查询该公司所有员工
	 */
	@Override
	public List<Emp> queryAllUsers() 
	{

		List<Emp> emps=deptMapper.queryAllUsers();
		return emps;
	}

	/**
	 * 根据子部门编号查询下属员工的信息
	 */
	@Override
	public List<Emp> queryEmpByDeptno(String deptno) 
	{

		return deptMapper.queryEmpByDeptno(deptno);
	}

	/**
	 * 根据上级领导部门查询子部门信息
	 */
	@Override
	public List<Dept> queryDeptByLeader(String leader) 
	{

		return deptMapper.queryAllDeptsByLeader(leader);
	}

	/**
	 * 根据部门号查询部门信息
	 */
	@Override
	public Dept getDeptByDeptno(String deptno) 
	{

		return deptMapper.getDeptByDeptno(deptno);
	}

	/**
	 * 根据部门名称查询部门信息
	 */
	@Override
	public Dept getDeptByDeptName(String deptName) 
	{

		return deptMapper.getDeptByDeptName(deptName);
	}

	/**
	 * 插入新的部门信息，根据用户名查询部门信息判断该部门是否存在则插入 否则抛出异常
	 */
	@Override
	public Dept createDept(Dept dept) 
	{

		Dept data = getDeptByDeptName(dept.getDeptName());
		if(data == null) {
			deptMapper.insertDept(dept);
		}else {
			throw new DemoException(
					"用户名(" + dept.getDeptName() + ")已经被注册！",ResponseResult.ERROR_DEPTNAME);
		}
		return dept;
	}

	/**
	 * 根据部门编号查询子部门信息，根据部门编号查询该部门所有员工的信息
	 * 只有当子部门下属员工的为空的时候或者下属部门为空的是后才能删除
	 * 否则提示不能删除
	 */
	@Override
	public Integer removeDept(String deptno) 
	{

		List<Emp> userList = queryEmpByDeptno(deptno);
		List<Dept> deptList = queryDeptByLeader(deptno);
		Integer row;
		if(userList.size()==0&&deptList.size()==0) {
			row=deptMapper.deleteDept(deptno);
			if(row!=1) {
				System.out.println("删除出现错误");
				throw new DemoException("删除失败",ResponseResult.ERROR_DELETE);
			}
			return row;
		}else {
			throw new DemoException("删除失败，有下属人员或部门",ResponseResult.ERROR_DELETE);
		}

	}

	/**
	 * 根据部门编号更新部门信息 只能更新部门名称和部门地址
	 * 逻辑：
	 *     根据部门编号查询部门信息如果为空则没有该部门抛出异常
	 *     如果不为空说明有该部门可以修改 
	 *     在修改的同事有可能多人是操作数据库所以抛出异常跟心失败
	 */
	@Override
	public Integer modifyDept(String deptName, String loc, String deptno) 
	{

		Dept data = getDeptByDeptno(deptno);
		if(data==null) {
			throw new DemoException("没有该部门",ResponseResult.ERROR_UPDATE);
		}else {
			Dept dept = getDeptByDeptName(deptName);
			if(dept == null) {
				data.setDeptName(deptName);
				data.setLoc(loc);
				Integer rows = deptMapper.updateDept(deptName, loc, deptno);
				if(rows!=1) {
					throw new DemoException("更新失败",ResponseResult.ERROR_UPDATE);
				}
			}else  {
				data.setLoc(loc);
				deptMapper.updateDept(deptName, loc, deptno);
			}
		}

		return 1;
	}



}
