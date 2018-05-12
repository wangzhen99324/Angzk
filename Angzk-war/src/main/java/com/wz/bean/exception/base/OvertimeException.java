package com.wz.bean.exception.base;

import com.wz.bean.exception.BaseException;
import com.wz.bean.exception.enums.ExceptionCause;


/**
 * 访问异常 抛出 408 请求超时 异常
 */
public class OvertimeException extends BaseException {
	private static final long serialVersionUID = -7303374187724238548L;

	@Override
	public String causedBy() {
		return ExceptionCause.overtime.name();
	}

}
