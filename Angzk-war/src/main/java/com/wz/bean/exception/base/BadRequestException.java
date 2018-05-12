package com.wz.bean.exception.base;

import com.wz.bean.exception.BaseException;
import com.wz.bean.exception.enums.ExceptionCause;


/**
 * 	访问异常  抛出 400
 *  坏的请求   参数不完整
 */
public class BadRequestException extends BaseException {
	private static final long serialVersionUID = -7303374187724238548L;

	@Override
	public String causedBy() {
		return ExceptionCause.badRequest.name();
	}

	
}
