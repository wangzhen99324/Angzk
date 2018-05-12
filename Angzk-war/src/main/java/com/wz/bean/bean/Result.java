package com.wz.bean.bean;

import java.io.Serializable;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.wz.bean.exception.base.ErrorMsgException;
import com.wz.bean.exception.enums.ErrorMsgEnum;


/**
 * 返回结果封装
 * 
 * @author Angzk
 */
public class Result implements Serializable, Cloneable {
	/**
	 * @author Angzk
	 */
	private static final long serialVersionUID = 8632964679110167394L;
	/**
	 * 错误信息
	 */
	private String msg;
	/**
	 * 错误编号
	 */
	private String code;
	/**
	 * 业务返回值
	 */
	private JSONObject biz_result;

	public Result() {
		super();
		this.msg = ErrorMsgEnum.SUCCESS.getMsg();
		this.code = ErrorMsgEnum.SUCCESS.getCode();
		this.biz_result = new JSONObject();
	}

	public Result(ErrorMsgException exception) {
		this.msg = exception.getMsg();
		this.code = exception.getCode();
		this.biz_result = new JSONObject();
	}

	public Result(ErrorMsgEnum errorMsgEnum, String msg) {
		this.msg = msg;
		this.code = errorMsgEnum.getCode();
		this.biz_result = new JSONObject();
	}

	public Result(ErrorMsgEnum msgEnum) {
		this.msg = msgEnum.getMsg();
		this.code = msgEnum.getCode();
		this.biz_result = new JSONObject();
	}

	public Result(Object biz_result) {
		this.msg = ErrorMsgEnum.SUCCESS.getMsg();
		this.code = ErrorMsgEnum.SUCCESS.getCode();
		if (JSONUtils.isNull(biz_result)) {
			this.biz_result = new JSONObject();
		} else if (JSONUtils.isArray(biz_result)) {
			JSONObject json = new JSONObject();
			json.put("list", biz_result);
			this.biz_result = json;
		} else if (JSONUtils.isString(biz_result)) {
			this.msg = String.valueOf(biz_result);
			this.biz_result = new JSONObject();
		} else if (JSONUtils.isBoolean(biz_result)) {
			JSONObject json = new JSONObject();
			json.put("status", biz_result);
			this.biz_result = json;
		} else {
			if (biz_result.getClass() == JSONObject.class) {
				this.biz_result = (JSONObject) biz_result;
			} else
				this.biz_result = JSONObject.fromObject(biz_result);
		}
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public JSONObject getBiz_result() {
		return biz_result;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBiz_result(Class<T> clazz) {
		return (T) JSONObject.toBean(biz_result, clazz);
	}

	public void setBiz_result(JSONObject biz_result) {
		this.biz_result = biz_result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"msg\":\"").append(msg);
		sb.append("\",\"code\":\"").append(code);
		sb.append("\",\"biz_result\":").append(biz_result);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public Result clone() {
		try {
			Result bean = (Result) super.clone();
			if (biz_result != null) {
				JSONObject json = new JSONObject();
				json.putAll(biz_result);
				bean.setBiz_result(json);
			}
			return bean;
		} catch (Exception e) {
			return null;
		}
	}

}
