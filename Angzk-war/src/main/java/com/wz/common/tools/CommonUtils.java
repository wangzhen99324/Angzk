package com.wz.common.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;

import com.wz.common.utils.DateUtils;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

@SuppressWarnings("restriction")
public class CommonUtils {

	/**
	 * 获取开始时间 和 结束时间 相差天数
	 * 
	 * @author Johnson.Jia
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static long getTimeDifference(long beginTime, long endTime) {
		// long time = CommonUtils.getDayZeroTimeInMillis(beginTime);
		return (beginTime - endTime) / (60 * 60 * 1000);
	}

	/**
	 * 正数 第一个大   负数 第二个大   0 一样
	 * @author Johnson.Jia
	 * @date 2017年2月21日 下午3:28:53
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int versionCheck(String version1,String version2){
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用.；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
	}
	
	/**
	 * 获取 request 所有参数 并封装到 Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParamValusToMap(
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Entry<String, String[]>> iterator = request.getParameterMap()
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) iterator
					.next();
			StringBuilder paramValue = new StringBuilder("[");
			for (String str : entry.getValue()) {
				paramValue.append("\"").append(str).append("\",");
			}
			map.put(entry.getKey(),
					paramValue.substring(0, paramValue.length() - 1) + "]");
		}
		return map;
	}

	/**
	 * 生成  不同 ip 下唯一 订单号
	 * 
	 * @return
	 */
	public static String getOrderNextNumber() {
		return new StringBuffer(DateUtils.getDateToString("yyMMddHHmmss",
				System.currentTimeMillis())).append(OrderAutoIncrement.Plus()).append(getRandomNumber(0, 9)).toString();
	}

	/**
	 * 拼音 字符串首字母大写
	 * 
	 * @author Johnson.Jia
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	/**
	 * 判断是否为空字符串
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isEmptyString(String src) {

		return src == null || src.trim().length() < 1;

	}

	/**
	 * 判断是否整数
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isInteger(String src) {

		try {

			Integer.parseInt(src);

		} catch (Exception e) {

			return false;

		}

		return true;
	}

	/**
	 * 判断是否Long型
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isLong(String src) {

		try {

			Long.parseLong(src);

		} catch (Exception e) {

			return false;

		}

		return true;
	}

	/**
	 * 将字符串转换为Int型:
	 * 
	 * 此方法将不会抛出NumberFormatException和NullPointerException，出现无法转换时， 则返回-1
	 * 
	 * @param s
	 * @return
	 */
	public static int parseInt(String s) {

		return parseInt(s, -1);

	}

	public static int parseInt(String s, int defaultValue) {

		try {

			return Integer.parseInt(s);

		} catch (Exception e) {

			return defaultValue;

		}
	}

	public static float parseFloat(String s, float defaultValue) {

		try {

			return Float.parseFloat(s);

		} catch (Exception e) {
			return defaultValue;

		}
	}

	public static double parseDouble(String s, double defaultValue) {

		try {

			return Double.parseDouble(s);

		} catch (Exception e) {
			return defaultValue;

		}
	}

	/**
	 * 将字符串转换为Long型:
	 * 
	 * 此方法将不会抛出NumberFormatException和NullPointerException，出现无法转换时， 则返回-1
	 * 
	 * @param s
	 * @return
	 */
	public static long parseLong(String s) {

		return parseLong(s, -1l);

	}

	/**
	 * 将字符串转换为Long型:
	 * 
	 * 此方法将不会抛出NumberFormatException和NullPointerException，出现无法转换时，
	 * 则返回defaultValue
	 * 
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	public static long parseLong(String s, long defaultValue) {
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String getRandomString() {
		return getRandomString(12);
	}

	/**
	 * 获取一个类BASE64编码的随机字符串
	 * 
	 * @param num
	 * @return
	 */
	public static String getRandomString(int num) {
		Random rd = new Random();
		StringBuilder content = new StringBuilder(num);

		for (int i = 0; i < num; i++) {
			int n;
			while (true) {
				n = rd.nextInt('z' + 1);
				if (n >= '0' && n <= '9')
					break;
				if (n >= 'a' && n <= 'z')
					break;
				if (n >= 'A' && n <= 'Z')
					break;
			}
			content.append((char) n);
		}
		return content.toString();
	}

	/**
	 * 获取一个从min到max的随机整数
	 * 
	 * @param min
	 * @param max
	 * @return [min, max]
	 */
	public static int getRandomNumber(int min, int max) {
		if (min >= max)
			return max;

		if (min + 1 == max)
			return min;

		return (int) Math.round(Math.random() * (max - min) + min);

	}

	/**
	 * 判断Email (Email由帐号@域名组成，格式为xxx@xxx.xx)<br>
	 * 帐号由英文字母、数字、点、减号和下划线组成，<br>
	 * 只能以英文字母、数字、减号或下划线开头和结束。<br>
	 * 域名由英文字母、数字、减号、点组成<br>
	 * www.net.cn的注册规则为：只提供英文字母、数字、减号。减号不能用作开头和结尾。(中文域名使用太少，暂不考虑)<br>
	 * 实际查询时-12.com已被注册。<br>
	 * 以下是几大邮箱极限数据测试结果<br>
	 * 163.com为字母或数字开头和结束。<br>
	 * hotmail.com为字母开头，字母、数字、减号或下划线结束。<br>
	 * live.cn为字母、数字、减号或下划线开头和结束。hotmail.com和live.cn不允许有连续的句号。
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {

		return isEmptyString(email) ? false
				: PatternUtils
						.regex("^[\\w_-]+([\\.\\w_-]*[\\w_-]+)?@[\\w-]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?$",
								email, true);
	}

	/**
	 * 从输入字符串中截取EMAIL
	 * 
	 * @param input
	 * @return
	 */
	public static String parseEmail(String input) {

		String regex = "[\\s\\p{Punct}]*([\\w_-]+([\\.\\w_-]*[\\w_-]+)?@[\\w-]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)?)[\\s\\p{Punct}]*";

		return PatternUtils.parseStr(input, regex, 1);
	}

	/**
	 * 判断是否为手机号
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {

		return isEmptyString(mobile) ? false : PatternUtils.regex(
				"^(\\+86(\\s)?)?0?1(3|4|5|7|8)\\d{9}$", mobile, true);

	}

	/**
	 * 将带有区号的手机号进行标准格式转化
	 * 
	 * @param mobile
	 * @return
	 */
	public static String getPhoneNumber(String phoneNumber, boolean mobileOnly) {

		if (isEmptyString(phoneNumber))
			return "";

		phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
		if (phoneNumber.startsWith("86"))
			phoneNumber = phoneNumber.replaceFirst("86", "");

		String ret = PatternUtils.parseStr(phoneNumber.replaceAll("\\s*", ""),
				"0?(1(3|4|5|8)\\d{9})", 1);

		return isMobile(ret) ? phoneNumber.startsWith("0") ? phoneNumber
				.replaceFirst("0", "") : phoneNumber : mobileOnly ? "" : ret;

	}

	/**
	 * 判断半角标点符号(仅 US-ASCII)
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isPunct(String src) {

		String regex = "\\p{Punct}";

		return PatternUtils.regex(regex, src, false);
	}

	/**
	 * String数组转化成以','分割的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String arrayToString(String[] strArray) {

		if (strArray == null || strArray.length < 1)
			return "";

		String s = Arrays.toString(strArray).replaceFirst("\\[", "");

		if (isEmptyString(s))
			return "";

		return s.substring(0, s.length() - 1);

	}

	/**
	 * List转化成以','分割的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static <E> String collectionToString(Collection<E> collection) {

		if (collection == null || collection.isEmpty())
			return "";

		String s = collection.toString().replaceFirst("\\[", "");

		if (isEmptyString(s))
			return "";

		return s.substring(0, s.length() - 1);

	}

	/**
	 * 末尾开始截取字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String subString(String str, int len) {

		if (CommonUtils.isEmptyString(str))
			return "";

		if (str.length() < len)
			throw new IllegalArgumentException(
					"The input string's length must>" + len);

		return str.substring(str.length() - len);

	}

	/**
	 * 按指定长度截取字符串
	 * 
	 * @param src
	 * @param length
	 * @return
	 */
	public static String intercept(String src, int length) {

		if (isEmptyString(src))
			return "";

		int len = src.length();
		int lng = src.getBytes().length;

		return lng < length ? src : src.substring(0,
				(int) ((length - 6) * len / lng)) + "…";
	}

	/**
	 * 获取传入小数的货币表现形式
	 * 
	 * @param money
	 * @return
	 */
	public static String formatMoney(double money) {

		if ((int) money == money)
			return Integer.toString((int) money);

		return formatMoney("0.00", money);

	}

	/**
	 * 获取传入小数的货币表现形式
	 * 
	 * @param format
	 *            指定的表现形式
	 * @param money
	 * @return
	 */
	public static String formatMoney(String format, double money) {

		DecimalFormat decimalFormat = new DecimalFormat(format);

		return decimalFormat.format(money);

	}

	/**
	 * 取文件后缀名
	 * 
	 * @param fileName文件名称
	 *            ，无后缀则返回""，有则返回.XX
	 * @return
	 */
	public static String getFilePostFix(String fileName) {
		return "."
				+ PatternUtils.parseStr(fileName, "^.+(\\.[^\\?]+)(\\?.+)?", 1)
						.replaceFirst("\\.", "");
	}

	/**
	 * 将内容以UTF8编码方式写入目标文件中,注意:此方法将覆盖原文件.
	 * 
	 * @param file
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static void writeFile(String file, String content)
			throws IOException {
		writeFile(file, content, "UTF-8");
	}

	/**
	 * 将内容以指定的编码方式写入目标文件中,注意:此方法将覆盖原文件.
	 * 
	 * @param file
	 * @param content
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static void writeFile(String file, String content, String charset)
			throws IOException {

		if (isEmptyString(file))
			throw new IOException("Can't write to an empty target file");

		if (isEmptyString(content))
			throw new IOException(
					"Can't write to file for the empty input param content");

		try {
			Charset.isSupported(charset);
		} catch (Exception e) {
			throw new IOException("Unsupported charset name: " + charset);
		}

		File f = new File(file);

		if (!f.exists()) {

			if (f.getParentFile() != null)
				f.getParentFile().mkdirs();

			f.createNewFile();

		}

		FileOutputStream fos = null;

		try {

			fos = new FileOutputStream(f);
			fos.write(content.getBytes(charset));

		} catch (IOException e) {

			throw e;

		} finally {

			if (null != fos)
				fos.close();

		}
	}

	/**
	 * 拷贝文件内容到目标文件中,注意:此方法将覆盖原目标文件.
	 * 
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(String sourceFile, String targetFile)
			throws IOException {

		File s = new File(sourceFile);
		if (!s.exists())
			throw new IOException("source file is not exists: " + sourceFile);

		File f = new File(targetFile);

		if (!f.exists()) {

			if (f.getParentFile() != null)
				f.getParentFile().mkdirs();

			f.createNewFile();

		}

		FileInputStream fis = new FileInputStream(s);
		FileOutputStream fos = new FileOutputStream(f);

		byte[] buf = new byte[1024];
		int i = 0;

		try {
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} finally {
			fis.close();
			fos.close();
		}

	}

	/**
	 * 手机归属地查询
	 * 
	 * @param mobile
	 * @return
	 */
	public static String mobileLocation(String mobile) {
		String result = WebUtils.getHtmlContent(new StringBuffer(
				"http://vip.showji.com/locating/?m=").append(mobile)
				.append("&outfmt=json").toString());
		result = result.replace("{", "").replace("}", "").replace("\"", "");
		String[] strs = result.split(",");
		Map<String, String> mobileInfo = new HashMap<String, String>();
		for (String str : strs) {
			String[] map = str.split(":");
			try {
				mobileInfo.put(map[0], map[1]);
			} catch (Exception e) {
			}
		}
		return mobileInfo.get("City");
	}

	/**
	 * 判断是否为电话号码
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {

		return PatternUtils
				.regex("(?s)\\+?(\\s*(\\(\\d+\\)|\\d+)\\s*)*(\\d-?)+\\d+",
						phone, true);

	}

	/**
	 * 获取JSONObject中指定key的字符串内容
	 * 
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getJSONValue(JSONObject json, String key) {
		try {
			String value = json.getString(key).trim();
			return CommonUtils.isEmptyString(value) ? "" : value;
		} catch (JSONException e) {
			return "";
		}
	}

	/**
	 * 对list随机出size个数据
	 * 
	 * @param list
	 * @param size
	 * @return
	 */
	public static List<Integer> random(List<Integer> list, int size) {
		if (list == null || list.size() < 1)
			return null;
		Collections.shuffle(list);
		return list.subList(0, size);
	}

	/**
	 * 对list分页取子列表
	 * 
	 * @param list
	 * @param size
	 * @return
	 */
	public static List<Integer> page(List<Integer> list, int pageFrom,
			int pageSize) {
		if (list == null || list.size() < 1)
			return null;
		return list.subList(pageFrom, pageFrom + pageSize);
	}

	/**
	 * 对密码字段进行SHA-256加密,并返回加密后的BASE64编码转换
	 * 
	 * @param pswd
	 *            注意:该字段必须不为空,且长度为 6 - 32
	 * @return
	 */
	public static String encodePassword(String pswd) {

		if (pswd == null || pswd.length() < 6 || pswd.length() > 32)
			throw new IllegalArgumentException(
					"Incorrect password! The password must not empty and it's length must between 6 and 32 bits long.");
		try {
			MessageDigest alga = MessageDigest.getInstance("SHA-256");

			alga.update(pswd.getBytes());

			byte[] hash = alga.digest();

			return new BASE64Encoder().encode(hash);

		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	/**
	 * 获取浏览器类型
	 * 
	 * @author Johnson.Jia
	 * @param request
	 * @return
	 */
	public static String getModel(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if(StringUtils.isEmpty(header)){
			return "UNKNOWN";
		}
		if(header.toUpperCase().contains("MICROMESSENGER")){
			return "WECHART";
		}
		UserAgent userAgent = UserAgent.parseUserAgentString(header);
		OperatingSystem os = userAgent.getOperatingSystem();
		String name = os.getName().toUpperCase();
		if (name.contains("ANDROID")) {
			return "ANDROID";
		}else if (name.contains("IPHONE")) {
			return "IOS";
		}else if (name.contains("WINDOWS")) {
			return "PC";
		}
		return "UNKNOWN";
	}

	public static void main(String[] args) {
		System.out.println(encodePassword("123456"));
	}
}
