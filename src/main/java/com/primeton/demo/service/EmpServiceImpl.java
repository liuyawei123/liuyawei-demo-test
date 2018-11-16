package com.primeton.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.primeton.demo.dao.IEmpMapper;
import com.primeton.demo.excption.DemoException;
import com.primeton.demo.model.ResponseResult;
import com.primeton.demo.model.Emp;
import com.primeton.demo.util.EmpUtil;

/**
 * 业务逻辑实现类，实现对数据的过滤和判断
 * @author liuyawei
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class EmpServiceImpl implements IEmpService {

	@Autowired
	IEmpMapper userMapper;
	/**
	 * 增
	 * 用户注册 先验证数据的格式 
	 */
	@Override
	public Emp createEmp(Emp emp) 
	{

		/**验证数据的格式正确性**/
		boolean result = EmpUtil.checkUsername(emp.getEmpName());
		if(!result) {
			throw new DemoException(
					"用户名格式错误，请您输入正确的用户格式",ResponseResult.ERROR_USERNAME_FORMAT);
		}

		//验证密码格式
		result = EmpUtil.checkPassword(emp.getPassword());
		if(!result) {
			throw new DemoException(
					"密码格式错误，请您输入正确的密码格式",ResponseResult.ERROR_PASSWORD_FORMAT);
		}
		
		//调用自身的查询用户名匹配的数据
		Emp data = getEmpByEmpName(emp.getEmpName());
		if (data == null) {
			Date now = new Date();
			emp.setCreatedUser(emp.getEmpName());
			emp.setCreatedDate(now);
			emp.setModifiedUser(emp.getEmpName());
			emp.setModifiedDate(now);
			userMapper.insertEmp(emp);
			return emp;
		} else {
			System.out.println("注册出现错误");
			throw new DemoException(
					"用户名(" + emp.getEmpName() + ")已经被注册！",ResponseResult.ERROR_USERNAME);
		}
	}
	
	/**
	 * 删
	 * 根据员工姓名删除员工信息
	 */
	@Override
	public Integer removeEmp(String empName) 
	{

		Integer row=userMapper.deleteEmp(empName);
		//在用已经登陆且用户名和sessin绑定的名字相同的情况下只有在通知操作数据库和该删除的操作的情况下才可能删除成功
		
		if(row!=1) {
			throw new DemoException("删除失败",ResponseResult.ERROR_DELETE);
		}
		return row;
	}
	
	/**
	 * 改
     * 修改用户个人信息，先判断密码和账户的正确性
     * 在根据用户名查询该用户是否存在，存在不许修改
     */
	@Override
	public Integer modifyEmp(String empName, String password,Integer id) {
		/**检查数据的有效性**/
		// 如果用户名格式错误，视为null
		if (!EmpUtil.checkUsername(empName)) {
			empName = null;
		}
		
		// 判断用户名是否为null，当为空的时候代表该用户名没有被注册过
		if (empName != null) {
			// 根据用户名查询用户所有信息你
			Emp data = getEmpByEmpName(empName);
			//如果查不到信息并且代表该用户id和用户名都为空 就是所有唯一标识为空可
			if (data == null) {
				//验证密码格式
				Boolean result = EmpUtil.checkPassword(password);
				if(!result) {
					throw new DemoException(
							"密码格式错误，请您输入正确的密码格式",ResponseResult.ERROR_PASSWORD_FORMAT);
				}
				// 将各参数都封装到User类型的对象中
				Emp emp = new Emp(empName, password);
				// 封装日志数据
				emp.setModifiedUser(emp.getEmpName());
				emp.setModifiedDate(new Date());
				emp.setId(id);
				// 调用持久层对象的changeInfo(User)方法，并获取返回值
				Integer rows = userMapper.updateEmp(emp);
				// 判断返回值是否为：1
				if (rows == 1) {
					// 如果为1表示修改成功
					return 1;
				} else {
					// 如果不为1：抛出异常UserNotFoundException 出现这种情况大概是有人在操纵的同时把你的数据删除了
					throw new DemoException("尝试操作的用户数据不存在！",ResponseResult.ERROR_USERNAME);
				}
			} else {
				// 如果id不匹配：用户名是别人：抛出异常UsernameConflictException
				throw new DemoException(
						"用户名(" + empName + ")已经被注册！");
			}
		}else {
			throw new DemoException(
					"用户名格式错误，请您输入正确的用户格式",ResponseResult.ERROR_USERNAME_FORMAT);
		}
		
	}
	
	/**
	 * 查
	 * 用户登录
	 */
	@Override
	public Emp login(String empName, String password) {
		// 调用自身的查询用户名匹配的数据
		Emp data = getEmpByEmpName(empName);
		// 判断是否查询到匹配的数据
		if (data != null) {
			/**
			 * 存在：用户名存在，得到了与用户名匹配的完整数据 data
			 */
			// 判断密码是否匹配
			if (data.getPassword().equals(password)) {
				//匹配：登陆成功，返回查询到的User对象
				return data;
			} else {
				// 不匹配：密码错误，抛出异常：PasswordNotMatchException
				throw new DemoException("密码错误！",ResponseResult.ERROR_PASSWORD);
			}
		} else {
			// 不存在：用户名不存在，即用户名错误
			// 抛出异常：UserNotFoundException
			throw new DemoException("用户名不存在！",ResponseResult.ERROR_USERNAME);
		}

	}
	
	/**
	 * 查
	 * 根据用具名查询用户个人信息
	 */
	@Override
	public Emp getEmpByEmpName(String empName) {
		Emp data = userMapper.getEmpByEmpName(empName);
		return data;
	}
    
}











