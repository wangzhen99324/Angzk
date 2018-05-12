package com.wz.bean.exception;

import java.net.SocketTimeoutException;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wz.bean.bean.Result;
import com.wz.bean.exception.base.BadRequestException;
import com.wz.bean.exception.base.ErrorMsgException;
import com.wz.bean.exception.base.NoAuthException;
import com.wz.bean.exception.enums.ErrorMsgEnum;

/**
 * Created by haokuixi on 2017/5/19.
 */
@RestControllerAdvice
public class ExceptionAdvice {

//  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(Exception.class)
  public Result handleServiceException(Exception e) {
		Result result = null;
			if (e instanceof ErrorMsgException) {
				result = new Result((ErrorMsgException) e);
			} else if (e instanceof NoAuthException) {
				result = new Result(ErrorMsgEnum.NoAuthException);
			} else if (e instanceof BadRequestException) {
				result = new Result(ErrorMsgEnum.badRequestException);
			} else {
//				if (e instanceof RpcException) {
//					result = new Result(new ErrorMsgException(ErrorMsgEnum.ERROR_ALERT, "当前服务正在维护升级，请稍候重试"));
//				}
				if (e instanceof DataAccessException) {
					result = new Result(ErrorMsgEnum.ERROR_SERVER,  " 服务数据库异常");
				}else if(e instanceof SocketTimeoutException ){
					result = new Result(ErrorMsgEnum.ERROR_SERVER,  " 服务数据库连接异常");
				}
				else {
					result = new Result(ErrorMsgEnum.ERROR_SERVER);
				}
			}
		return result;
  }
}
