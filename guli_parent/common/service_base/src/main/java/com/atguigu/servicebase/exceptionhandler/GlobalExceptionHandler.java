package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	//出现什么异常时执行该方法
	@ExceptionHandler(Exception.class)
	@ResponseBody    //为了返回数据，也就是在执行完该方法后返回json数据给前面
	public R error(Exception e){
		e.printStackTrace();
		log.error(e.getMessage());
		return R.error().message("执行了全局异常处理");
	}

	//出现什么异常时执行该方法
	@ExceptionHandler(GuliException.class)
	@ResponseBody    //为了返回数据，也就是在执行完该方法后返回json数据给前面
	public R error(GuliException e){
		e.printStackTrace();
		return R.error().code(e.getCode()).message(e.getMsg());
	}
}