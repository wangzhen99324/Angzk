package com.wz.bean.exception.base;

import com.wz.bean.exception.BaseException;
import com.wz.bean.exception.enums.ExceptionCause;


/**
 * 未授权 绑定 不能登录
 * 
 * @author Johnson.Jia
 * @date 2017年6月21日 下午10:47:30
 */
public class BindingUnauthorized extends BaseException {

	/**
	 * @author Johnson.Jia
	 * @date 2017年6月21日 下午10:47:48
	 */
	private static final long serialVersionUID = 7831876490690071547L;

	@Override
	public String causedBy() {
		return ExceptionCause.bindUnauth.name();
	}

}
