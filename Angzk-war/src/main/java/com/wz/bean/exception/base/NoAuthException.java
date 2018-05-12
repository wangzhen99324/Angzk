package com.wz.bean.exception.base;

import com.wz.bean.exception.BaseException;
import com.wz.bean.exception.enums.ExceptionCause;





/**
 * 用户未登录  抛出 403
 */
public class NoAuthException extends BaseException {
	private static final long serialVersionUID = 8199514192443835496L;

	@Override
	public String causedBy() {
		return ExceptionCause.noAuth.name();
	}

}
