package com.wz.common.consts;


/**
 * 系统常量配置
 * 
 * @author Johnson.Jia
 */
public class Consts {
	/**
	 * 是否为 生产环境 true 生产 false 测试
	 */
//	public static final boolean SYSTEM_IS_RELEASE = SysConfig.sysConfig.getProperty("version").equals("release");

	/**
	 * 日志打印 logger
	 */
	public static final String LOGGER_INFO_FORMAT = "[PV][{0}][{1}][{2}][{3}][{4}][{5}\"code\":\"{6}\",\"msg\":\"{7}\"}]";

	public static final String LOGGER_ERROR_FORMAT = "[ER][{0}][{1}][{2}][{3}][{4}][{5}][{6}]";

	public static final String LOG_ERROR_EH_CACHE = "【========= EhCache 缓存异常 =========】";

	public static final String LOG_ERROR_REDIS_CACHE = "【========= Redis 缓存异常 =========】";

}
