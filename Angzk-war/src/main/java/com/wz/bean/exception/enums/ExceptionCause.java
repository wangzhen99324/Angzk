package com.wz.bean.exception.enums;

/**
 *  异常处理 枚举类
 */
public enum ExceptionCause {
	
	//第三方登录 未授权						409
	bindUnauth(409),					
	//坏的请求 错误的请求  参数不完整	  			400
	badRequest(400),
	// 用户在其他设备登录  当前token认证无效	  	401
	unauthorized(401),
	//无授权 没有登录							403
	noAuth(403), 
	//请求超时 								408
	overtime(408), 
	//自定义错误异常							200
	ErrorMessages(200);

	private ExceptionCause(int statusCode) {
		this.statusCode = statusCode;
	}

	private int statusCode;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
