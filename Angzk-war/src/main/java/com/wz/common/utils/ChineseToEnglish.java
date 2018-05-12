package com.wz.common.utils;

//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;

/**
 * 中英文处理
 * 
 * @author Johnson.Jia
 */
public class ChineseToEnglish {

	/**
	 * 效验是否全部为 中文
	 * 
	 * @author Johnson.Jia
	 * @param text
	 * @return
	 */
	public static boolean isCheckString(String text) {
		if (StringUtils.isBlank(text)) {
			return false;
		}
		char[] t1 = text.toCharArray();
		for (int i = 0; i < t1.length; i++) {
			char c = t1[i];
			if (!java.lang.Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 效验是否 存在 中文
	 * 
	 * @author Johnson.Jia
	 * @param text
	 * @return
	 */
	public static boolean existCheckString(String text) {
		char[] t1 = text.toCharArray();
		for (int i = 0; i < t1.length; i++) {
			char c = t1[i];
			if (java.lang.Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 只保留 中文 字符
	 * 
	 * @author Johnson.Jia
	 * @return
	 */
	public static String getTextZh(String text) {
		char[] t1 = text.toCharArray();
		StringBuilder sb = new StringBuilder();
		int t0 = t1.length;
		for (int i = 0; i < t0; i++) {
			char c = t1[i];
			if (java.lang.Character.toString(c).matches("[\\u4E00-\\u9FA5、，,]+")) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 只保留 中英文 字符
	 * 
	 * @author Johnson.Jia
	 * @return
	 */
	public static String getTextZhAndEh(String text) {
		char[] t1 = text.toCharArray();
		StringBuilder sb = new StringBuilder();
		int t0 = t1.length;
		for (int i = 0; i < t0; i++) {
			char c = t1[i];
			if (java.lang.Character.toString(c).matches("[\\u4E00-\\u9FA5a-zA-Z]+")) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 效验是否全部为 数字
	 * 
	 * @author Johnson.Jia
	 * @return
	 */
	public static boolean isCheckNumber(String text) {
		if (StringUtils.isBlank(text)) {
			return false;
		}
		char[] t1 = text.toCharArray();
		for (int i = 0; i < t1.length; i++) {
			char c = t1[i];
			if (!Character.toString(c).matches("[0-9]+")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 剔除标点符号
	 * 
	 * @author Johnson.Jia
	 * @return
	 */
	public static String getText(String text) {
		if (StringUtils.isBlank(text)) {
			return "";
		}
		char[] t1 = text.toCharArray();
		StringBuilder sb = new StringBuilder();
		int t0 = t1.length;
		for (int i = 0; i < t0; i++) {
			char c = t1[i];
			if (java.lang.Character.toString(c).matches("[\\u4E00-\\u9FA5a-zA-Z0-9]+")) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 将汉字转换为全拼
	 * 
	 * @author Johnson.Jia
	 * @param src
	 * @return
	 */
	public static String getPingYin(String src) {
		char[] t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					if (t2.length > 0) {
						t4 += t2[0];
					}
				} else
					t4 += java.lang.Character.toString(t1[i]);
			}
			return t4;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4;
	}

	/**
	 * 返回中文拼音的首字母
	 * 
	 * @author Johnson.Jia
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	/**
	 * 效验是否全为 字母
	 * 
	 * @author Johnson.Jia
	 * @date 2016年11月24日 下午6:03:34
	 * @param text
	 * @return
	 */
	public static boolean isCheckEh(String text) {
		char[] t1 = text.toCharArray();
		for (int i = 0; i < t1.length; i++) {
			char c = t1[i];
			if (!Character.toString(c).matches("[a-zA-Z]+")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串转移为ASCII码
	 * 
	 * @author Johnson.Jia
	 * @param cnStr
	 * @return
	 */
	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();
		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getPinYinHeadChar("人参"));
	}
}