package com.wz.common.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 时间处理 工具类
 * 
 * @author Johnson.Jia
 */
public class DateUtils {
	/**
	 * 最大日期
	 */
	private final static int[] maxDay = new int[] { 31, 29, 31, 30, 31, 30, 31,
			31, 30, 31, 30, 31 };

	/**
	 * 输入的年月日是否为正确日期
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static boolean isRealDate(int year, int month, int day) {

		if (year < 0 || month < 1 || day < 1)
			return false;

		if (month > 12 || day > 31)
			return false;

		if (day > maxDay[month - 1])
			return false;

		if (month == 2 && day > 28) {

			if (year % 4 != 0 || year % 100 == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 根据指定日期 会员续费天数 如果指定日期小于今天 就以今日为起点
	 * 
	 * @author Johnson.Jia
	 * @param specified
	 *            会员日期
	 * @param number
	 *            购买数量 续费数量
	 * @param day
	 *            每次多少天 续费单位
	 * @return
	 * @throws Exception
	 */
	public static long userPeriod(long specified, int number, int day) {
		long today = System.currentTimeMillis();
		if (specified < today) {
			specified = today;
		} else {
			specified += 1000;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(specified);
		cal.add(Calendar.DAY_OF_MONTH, day * number - 1); // 增加多少天
		cal.set(Calendar.HOUR_OF_DAY, 23); // 设置为当日23点
		cal.set(Calendar.MINUTE, 59); // 59分
		cal.set(Calendar.SECOND, 59); // 59秒
		cal.set(Calendar.MILLISECOND, 0); // 0毫秒
		return cal.getTimeInMillis();
	}
	
	/**
	 * 格式化    当前时间   yyyy-MM-dd HH:mm:ss
	 * @author Johnson.Jia
	 * @param millis
	 * @return
	 */
	public static String getDateToString() {
		return getDateToString(null,System.currentTimeMillis());
	}

	/**
	 * 格式化  时间 yyyy-MM-dd HH:mm:ss
	 * @author Johnson.Jia
	 * @param millis
	 * @return
	 */
	public static String getDateToString(long millis) {
		return getDateToString(null,millis);
	}
	/**
	 * 将时间戳转换为格式化的字符串
	 * 
	 * @param format
	 *            默认 yyyy-MM-dd HH:mm:ss
	 * @param millis
	 * @return
	 */
	public static String getDateToString(String format, long millis) {
		Date date = new Date(millis);
		if (StringUtils.isBlank(format))
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);

	}

	/**
	 * 返回指定日期格式字符串的时间戳
	 * 
	 * @param format
	 *            eg: yyyy-MM-dd HH:mm
	 * @param time
	 * @return
	 */
	public static long parseDateString(String format, String time) {

		try {

			return new SimpleDateFormat(format).parse(time).getTime();

		} catch (Exception e) {

			return 0;

		}

	}

	/**
	 * 判断时间戳是否为今天
	 * 
	 * @param millis
	 * @return
	 * @throws Exception
	 */
	public static boolean isToday(long millis) throws Exception {

		long oneday = 24 * 60 * 60 * 1000l;
		long utc = 8 * 60 * 60 * 1000l;

		return (System.currentTimeMillis() + utc) / oneday - (millis + utc)
				/ oneday == 0;

	}

	/**
	 * 获取指定日期时间戳
	 * 
	 * @param dateFormat
	 * @param date
	 * @return
	 */
	public static long getTimeStamp(String dateFormat, String dateString) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			Date date = (Date) df.parse(dateString);
			return date.getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * 获取 当前时间 增加相应月数的时间戳
	 * 
	 * @author Johnson.Jia
	 * @date 2015年11月20日 下午8:27:32
	 * @param month
	 * @return
	 */
	public static long getDateTimeByMonth(long millis, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		cal.add(Calendar.MONTH, month); // 增加自然月
		return cal.getTimeInMillis();
	}

	/**
	 * 返回n天后的日期零点时间戳
	 * 
	 * @author Johnson.Jia
	 * @param day
	 *            可以为任何整数，负数表示前N天，正数表示后N天
	 * @return
	 */
	public static long getFutureInMillis(int days) {
		return getFutureInMillis(-1, days);
	}

	/**
	 * 返回 指定日期 n天后的日期零点时间戳
	 * 
	 * @author Johnson.Jia
	 * @param millis
	 *            大于 0 有效
	 * @param days
	 *            可以为任何整数，负数表示前N天，正数表示后N天
	 * @return
	 */
	public static long getFutureInMillis(long millis, int days) {
		Calendar cal = Calendar.getInstance();
		if (millis > 0) {
			cal.setTimeInMillis(millis);
		}
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + days); // 当前日期增加天数
		cal.set(Calendar.HOUR_OF_DAY, 0); // 设置为当日0点
		cal.set(Calendar.MINUTE, 0); // 0分
		cal.set(Calendar.SECOND, 0); // 0秒
		cal.set(Calendar.MILLISECOND, 0); // 0毫秒
		return cal.getTimeInMillis();
	}

	/**
	 * 获取当前 日期 周一初始 时间
	 * 
	 * @author Johnson.Jia
	 * @param millis
	 * @return
	 */
	public static long getWeekInitTime(long millis) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		if (millis > 0) {
			cal.setTimeInMillis(millis);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0); // 设置为当日0点
		cal.set(Calendar.MINUTE, 0); // 0分
		cal.set(Calendar.SECOND, 0); // 0秒
		cal.set(Calendar.MILLISECOND, 0); // 0毫秒
		return cal.getTimeInMillis();
	}

	/**
	 * 获取指定日期内的 周一 至 周末的时间
	 * 
	 * @author Johnson.Jia
	 * @param millis
	 *            小于等于 0 采用当前时间
	 * @return
	 */
	public static Map<Integer, Integer> getWeekdays(long millis) {
		Map<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
		Calendar calendar = Calendar.getInstance();
		if (millis > 0) {
			calendar.setTimeInMillis(millis);
		}
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		for (int i = 1; i < 8; i++) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			result.put(i,
					Integer.valueOf(dateFormat.format(calendar.getTime())));
			calendar.add(Calendar.DATE, 1);
		}
		return result;
	}

	/**
	 * 根据生日 计算年龄
	 * 
	 * @author Johnson.Jia
	 * @param birthday
	 *            2016-05-66 yyyy-MM-dd
	 * @return
	 */
	public static Integer getAge(String birthday) {
		long time = System.currentTimeMillis();
		long day = (time - getTimeStamp("yyyy-MM-dd", birthday))
				/ (24 * 60 * 60 * 1000) + 1;
		String year = new DecimalFormat("#").format(day / 365f);
		return Integer.valueOf(year);
	}

	// TEST
	public static void main(String args[]) throws Exception {
		System.out.println(System.currentTimeMillis());
		System.out.println(parseDateString("yyyy-MM-dd HH:mm:ss",
				"2016-09-16 00:00:00"));
		System.out.println(getDateToString("yyyy-MM-dd HH:mm:ss",
				1472116187303l));
	}

}
