package com.primeton.demo.service;

import java.util.List;

import com.primeton.demo.model.Dept;
import com.primeton.demo.model.Emp;
/**
 * 组织信息业务逻辑
 * @author liuya
 *
 */
public interface IOrgService {
	
	/**
	 * 查询该公司所有员工
	 * @return 该公司所有员工集合
	 */
	public List<Emp> queryAllUsers();
	
	/**
	 * 根据该部门号查询该部门的全部人员的信息
	 * @param deptno  部门编号
	 * @return  该部门全部人员信息集合
	 */
	public List<Emp> queryEmpByDeptno(String deptno);
	

	/**
	 * 根据部门号查询附属部门
	 * @param deptno 部门号
	 * @return   子部门数据信息对象集合
	 */
	public List<Dept> queryDeptByLeader(String leader);
	
	/**
	 * 添加部门
	 * @param dept  部门数据存放的对象
	 * @return   受影响的行数
	 */
	public Dept createDept(Dept dept);
	/**
	 * 根据部门名称获取该部门的信息
	 * @param deptname 部门名称
	 * @return 该部门信息对象
	 */
	public Dept getDeptByDeptName(String deptname);
	
	/**
	 * 根据部门号删除部门
	 * 分析：
	 *     删除高级部门是要判断是否有下属部门存在 如果存在则提示不可删除或者先删除下属部门
	 *     如果没有下属部门则 就要查询是否有下属人员信息如果有则提示不能删除或者提示删除下属人员
	 * @param deptno  部门号
	 * @return 受影响的行数
	 */
	public Integer removeDept(String deptno);
	
	/**
	 * 根据部门号修过部门名称和部门地址
	 * @return 受影响的行数
	 */
	public Integer modifyDept(String deptName,String loc,String deptno);
	
	/**
	 * 根据部门号获取该部门的信息
	 * @param deptno 部门编号
	 * @return 部门名称
	 */
	public Dept getDeptByDeptno(String deptno);
}
