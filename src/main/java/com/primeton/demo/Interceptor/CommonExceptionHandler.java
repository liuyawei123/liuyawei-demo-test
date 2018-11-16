package com.primeton.demo.Interceptor;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.primeton.demo.excption.DemoException;
import com.primeton.demo.model.ResponseResult;

/**
 * 统一异常处理
 * 这里需要加配置才能读出404这一类的异常json
 * @author liuya
 *
 */
@RestControllerAdvice
public class CommonExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseResult<Void> handleServiceException(
			HttpServletRequest req, HttpServletResponse res, Exception e) {

		logger.info("进入异常处理界面...");

		if (e instanceof NullPointerException) {
			logger.error("很遺憾您出現了" + e.getMessage()+"请及时处理", e);
			return new ResponseResult<Void>(00,"发生空指针异常");
		}else if (e instanceof IllegalArgumentException) {
			logger.error("很遺憾您出現了" + e.getMessage()+"请及时处理", e);
			return new ResponseResult<Void>(01,"请求参数类型不匹配");
		}else if (e instanceof SQLException) {
			logger.error("很遺憾您出現了" + e.getMessage()+"请及时处理", e);
			return new ResponseResult<Void>(02,"数据库访问异常,请检查数据库代码");
		}else if (e instanceof NoHandlerFoundException) {
			logger.error("很遺憾您出現了" + e.getMessage()+"请及时处理", e);
			return new ResponseResult<Void>(404,"访问地址错误，请输出正确的地址");
		}else if (e instanceof DemoException) {
			logger.error("很遺憾您出現了" + e.getMessage()+"请及时处理", e);
			DemoException demoException = (DemoException)e;
			return new ResponseResult<Void>(demoException.getErrCode(), demoException.getMessage());
		}else {
			logger.error("很遺憾您出現了" + e.getMessage()+"请及时处理", e);
			return new ResponseResult<Void>(500,"代码出现异常请修改代码");
		}
	}
}	







