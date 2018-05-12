package com.wz.bean.exception.base;

import com.wz.bean.exception.BaseException;
import com.wz.bean.exception.enums.ExceptionCause;


/**
 * 用户已在其他设备登录 当前认证无效 抛出 401
 * 
 * @author Johnson.Jia
 */
public class UnauthorizedException extends BaseException {

	private static final long serialVersionUID = 8088934606432865810L;

	@Override
	public String causedBy() {
		return ExceptionCause.unauthorized.name();
	}
}
