package com.wz.bean.exception;

/**
 * 异常基类
 * 
 * @author Angzk
 */
public abstract class BaseException extends Exception {

	/**
	 * @author Angzk
	 */
	private static final long serialVersionUID = -5420974096907472856L;

	public BaseException() {
		super();
	}

	public BaseException(String err) {
		super(err);
	}

	public abstract String causedBy();

}
