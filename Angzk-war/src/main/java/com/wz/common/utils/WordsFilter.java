package com.wz.common.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wz.common.tools.CommonUtils;
import com.wz.common.tools.PatternUtils;


public class WordsFilter {

	static final char[] fbdChar = { '!', '#', '$', '%', '^', '&', '*', ',',
			'.', '?', '_', '"', '\'', '>', '<', '\\', '/' };

	static final char[] fbdCharReplace = { '！', '＃', '＄', '％', '＾', '＆', '×',
			'，', '。', '？', '_', '“', '‘', '>', '<', '\\', '/' };

	public static boolean containForbiddenChar(String name) {
		if (name == null)
			return true;
		for (int i = 0; i < fbdChar.length; i++) {
			if (name.indexOf(fbdChar[i]) > -1) {
				return true;
			}
		}
		return false;
	}

	public static String replaceForbiddenChar(String name) {
		new java.lang.Throwable().printStackTrace();
		return name;
	}

	/**
	 * 对含有会造成页面打不开字符的文字进行过滤， 如果里面含有ｕｒｌ链接，则不要使用这个过滤， 半角的&会被全角的＆替代
	 * 
	 * @return String
	 */
	public static String transInput(String in) {
		if (in == null || in.trim().equals("")) {
			return "";
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '#') {
				out.append("＃");
			} else if (c == '\'') {
				out.append("@");
			} else if (c == '\"') {
				out.append("@");
			} else if (c == '&') {
				out.append("＆");
			} else if (c == '%') {
				out.append("％");
			} else if (c == '$') {
				out.append("＄");
			} else if (c >= 0 && c < 32) {
				out.append("X");
			} else {
				out.append(c);
			}
		}
		return out.toString();
	}

	/**
	 * 将传入字符串中的所有超链接替换成在新窗口打开
	 * 
	 * @param in
	 *            将被替换的字符串
	 * @return 返回被替换了的字符串
	 */
	public static String toBlankHref(String in) {

		if (CommonUtils.isEmptyString(in)) {
			return "";
		}

		return PatternUtils
				.replace("<a([^>]+)>", "<a$1 target=\"_blank\">", in);

	}

	/**
	 * 这个方法用来把'<' '>' ' '替换成'&lt;' '&gt;' '&nbsp;'
	 * 
	 * @param in
	 *            将被替换的字符串
	 * @return 返回被替换了的字符串
	 */
	public static String toHTMLString(String in) {

		return toHTMLString(in, false);

	}

	/**
	 * 这个方法用来把'<' '>' ' '替换成'&lt;' '&gt;' '&nbsp;'
	 * 
	 * @param in
	 *            将被替换的字符串
	 * @param replaceHref
	 *            是否替换网址为超链接形式.
	 * @return 返回被替换了的字符串
	 */
	public static String toHTMLString(String in, boolean replaceHref) {

		if (CommonUtils.isEmptyString(in)) {
			return "";
		}

		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\'') {
				out.append("&#39;");
			} else if (c == '\"') {
				out.append("&#34;");
			} else if (c == '<') {
				out.append("&lt;");
			} else if (c == '>') {
				out.append("&gt;");
				/*
				 * } else if (c == '&') { out.append("&amp;");
				 */
			} else if (c == '%') {
				out.append("&#37;");
			} else if (c == '$') {
				out.append("＄");
			} else {
				out.append(c);
			}
		}
		if (replaceHref) {
			String ret = out.toString();
			out.delete(0, out.length());
			Pattern p = Pattern
					.compile(
							"((\\w+):\\/\\/)?((([\\-\\w]+\\.)+[a-z]+)|((\\d+\\.){3}\\d+))(\\:\\d+)?(\\/([\\p{Punct}\\w]*))*(\\?[\\p{Punct}\\w]*)?",
							Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(ret);
			while (m.find()) {
				String href = m.group();
				m.appendReplacement(out,
						"<a href=\""
								+ (!href.startsWith("http") ? "http://" + href
										: href) + "\" target=\"_blank\">"
								+ href + "</a>");
			}
			m.appendTail(out);
		}
		return out.toString().replace("\r", "<br/>");
	}

	/**
	 * 转义正则表达式中有特殊含义的字符
	 * 
	 * @param compile
	 * @return
	 */
	public static String tranCompile(String compile) {
		if (compile == null || compile.equals("")) {
			return compile;
		}
		compile = compile.replace("\\", "\\\\");
		compile = compile.replace("[", "\\[");
		compile = compile.replace("]", "\\]");
		compile = compile.replace("*", "\\*");
		compile = compile.replace(".", "\\.");
		compile = compile.replace("(", "\\(");
		compile = compile.replace(")", "\\)");
		compile = compile.replace("+", "\\+");
		compile = compile.replace("}", "\\}");
		compile = compile.replace("{", "\\{");

		return compile;
	}

	/**
	 * 全角、半角转换 全角65281-65374 对应 半角33-126 相差:65248 其它情况:空格(全角-半角:12288-32)
	 * 
	 * @param str
	 *            String 待转字符串
	 * 
	 * 
	 * 
	 * @return String 处理后的字符串
	 * 
	 * 
	 * 
	 */
	private static HashMap<Integer, Character> sbc2dbc = new HashMap<Integer, Character>(); // 全角对应半角

	public static String SBC2DBCcase(String str) {
		if (str == null || str.equals("")) {
			return str;
		}
		StringBuffer bu = new StringBuffer();
		char[] ch = str.toCharArray();
		int tmp = 0;
		for (int i = 0; i < ch.length; i++) {
			tmp = (int) ch[i];
			if (tmp >= 65281 && tmp <= 65374) {
				bu.append((char) (tmp - 65248));
			} else if (sbc2dbc.containsKey(tmp)) {
				bu.append(sbc2dbc.get(tmp));
			} else {
				bu.append(ch[i]);
			}
		}
		return bu.toString();
	}

	/**
	 * 半角、全角转换 全角65281-65374 对应 半角33-126 相差:65248 其它情况:空格(全角-半角:12288-32)
	 * 
	 * @param str
	 *            String 待转字符串
	 * 
	 * 
	 * 
	 * @return String 处理后的字符串
	 * 
	 * 
	 * 
	 */
	private static HashMap<Integer, Character> dbc2sbc = new HashMap<Integer, Character>(); // 半角对应全角

	public static String DBC2SBCcase(String str) {
		if (str == null || str.equals("")) {
			return str;
		}
		StringBuffer bu = new StringBuffer();
		char[] ch = str.toCharArray();
		int tmp = 0;
		for (int i = 0; i < ch.length; i++) {
			tmp = (int) ch[i];
			if (tmp >= 32 && tmp <= 126) {
				bu.append((char) (tmp + 65248));
			} else if (sbc2dbc.containsKey(tmp)) {
				bu.append(dbc2sbc.get(tmp));
			} else {
				bu.append(ch[i]);
			}
		}
		return bu.toString();
	}

	/***************************************************************************
	 * gui 2008-01-14 添加 匹配规则: 1.大小写无关 2.敏感字符中使用正则表达式无效 3.以下全、半角字符无关(以及空格)
	 * 全角！＂＃＄％＆＇（）＊＋，－．／０到９：；＜＝＞？＠Ａ到Ｚ［＼］＾＿｀ａ到ｚ｛｜｝～ 半角!"#$%&'()*+,-./0到9:;
	 * <=>?@A到Z[\]^_`a到z{|}~ 4.建议定义:允许每个敏感字符可以间隔N个某些指定的字符,扩大限制范围，减少敏感词汇
	 * 
	 * 
	 * @author gui
	 * 
	 **************************************************************************/

	public static final int NAME = 1; // 1.禁止词

	public static final int SEARCH = 2; // 2.搜索屏蔽词

	public static final int INPUT = 3; // 3.输入过滤

	public static final int NOTSEARCH = 1; // 不可搜索
	public static final int NOTSEARCHHOT = 2; // 不可热门

	/**
	 * 替换敏感字符,如有,则会将相关的全角字符替换成半角
	 * 
	 * 
	 * @param src
	 *            String 待处理字符串
	 * @param oldstr
	 *            String 查找规则
	 * @param newstr
	 *            String 预定义的新的替换字符串
	 * 
	 * @return String
	 */
	private static String filter(String src, String oldstr, String newstr) {
		String newsrc = SBC2DBCcase(src);
		try {
			// return src.replaceAll(oldstr, newstr);
			Pattern p = Pattern.compile(oldstr, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(newsrc);
			if (m.find()) {
				return m.replaceAll(newstr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return src;
	}

	/**
	 * 查找待处理字符串中是否包含了敏感字符
	 * 
	 * @param src
	 *            String 待处理字符串
	 * @param oldstr
	 *            String 查找规则
	 * @return String
	 */
	private static boolean filter(String src, String oldstr) {
		String newsrc = SBC2DBCcase(src);
		try {
			Pattern p = Pattern.compile(oldstr, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(newsrc);
			return m.find();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否允许使用(禁止字词)
	 * 
	 * @param uid
	 *            String 用户ID
	 * @param name
	 *            String 用户呢称
	 * @return boolean true 允许使用; false 禁止使用
	 */
	public static boolean isAllowForbid(String name) {
		if (names == null || name == null || name.trim().equals("")) {
			return true;
		}
		String[] result = null;
		for (int i = 0; i < names.length; i++) {
			result = names[i];
			if (result != null && !result[0].equals("")) {
				if (filter(name, result[0])) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 搜索词汇过滤 仅过滤不可搜索词汇
	 * 
	 * 
	 * @param src
	 *            String 待处理的搜索字词
	 * @return String 处理后的搜索字词
	 */
	public static String filterSearch(String src) {
		if (searchs == null || src == null || src.equals("")) {
			return src;
		}
		String[] result = null;
		for (int i = 0; i < searchs.length; i++) {
			result = searchs[i];
			if (result != null && !result[0].equals("")
					&& result[3].equals(Integer.toString(NOTSEARCH))) {
				if (filter(src, result[0])) {
					src = filter(src, result[0], result[1]);
				}
			}
		}
		return src;
	}

	/**
	 * 是否要在热门搜索列表中显示
	 * 
	 * 
	 * @param src
	 *            String 待处理的搜索字词
	 * @return boolean true 不显示,false 显示
	 */
	public static boolean isNotHotSearch(String src) {
		if (searchs == null || src == null || src.equals("")) {
			return false;
		}
		String[] result = null;
		for (int i = 0; i < searchs.length; i++) {
			result = searchs[i];
			if (result != null && !result[0].equals("")) {
				if (filter(src, result[0])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 输入内容过滤
	 * 
	 * @param src
	 *            String 待处理的输入字符串
	 * 
	 * @return String 处理后的字符串
	 * 
	 */
	public static String filterInput(String src) {
		if (inputs == null || src == null || src.equals("")) {
			return src;
		}
		String[] result = null;
		for (int i = 0; i < inputs.length; i++) {
			result = inputs[i];
			if (result != null && !result[0].equals("")) {
				if (filter(src, result[0])) {
					src = filter(src, result[0], result[1]);
				}
			}
		}
		return src;
	}

	/**
	 * 是否允许使用(输入内容过滤)
	 * 
	 * @param src
	 *            String 待处理的输入字符串
	 * 
	 * @return boolean true:允许;false:内容有敏感词汇
	 * 
	 */
	public static boolean isAllowInput(String src) {
		if (inputs == null || src == null || src.equals("")) {
			return true;
		}
		String[] result = null;
		for (int i = 0; i < inputs.length; i++) {
			result = inputs[i];
			if (result != null && !result[0].equals("")) {
				if (filter(src, result[0])) {
					return false;
				}
			}
		}
		return true;
	}

	// 数组格式:敏感字词/替换准备字词/用户ID/分类属性

	public static int maxNum = 2000;

	private static String[][] names = null; // 禁止词

	private static String[][] searchs = null; // 搜索屏蔽词

	private static String[][] inputs = null; // 输入过滤

	synchronized public static void loadWordsDB(String[][] name_array,
			String[][] search_array, String[][] input_array) {

		names = name_array;

		searchs = search_array;

		inputs = input_array;

	}

	static {
		sbc2dbc.put(12288, '　');
		dbc2sbc.put(32, ' ');
	}

	public static String escapeHTML(String src) {

		return CommonUtils.isEmptyString(src) ? "" : src.replaceAll("<", "＜")
				.replaceAll(">", "＞");

	}

	public static void main(String[] args) {

		System.out
				.println(toBlankHref("仅需6元就可购买童年时光最喜爱的旺旺食品的浪味仙五包装之蔬菜口味（原价12.5元）。"
						+ "采用密封包装，不会因为运输的原因而影响食品质量。浏览<a href=\"http://www.wantwant.com.cn\">旺旺食品</a>"
						+ "可以了解更多详情</p><p>&nbsp;</p><p><strong>【购买】</strong>本单从易起买下单付款后，只需<strong>等待快递送货</strong>"
						+ "到你手里就可以啦。无需邮费，请尽情购买！我们会委托旺旺食品负责销售及配送。送货地址<strong>上海市郊</strong>，崇明的用户暂不能参加本次团购。"
						+ "</p><p>&nbsp;</p><p><strong>【浪味仙】</strong>浪味仙属马铃薯类膨化食品，俗称洋芋点心，是旺旺公司较早投入休闲食品市场之一；"
						+ "其独特的“DNA”造型，比切片的薯片不易碎，保持完整口感，一经上市即引起市场热销；曾在90年代，蔬菜口味曾创造年销售额过亿元的业绩；"
						+ "网络时代浪味仙的口碑在网络上好评连连，怀旧依依,<a href=\"http://www.douban.com/group/topic/2826181/\">在豆瓣网上引起热议</a>；"
						+ "消费者更有<a href=\"http://www.92dp.com/dianping/5279.htm\">自拍“好吃的浪味仙”的广告</a> ；新包装具有中文对照，强化国际品牌效率，"
						+ "浪味仙是旺旺公司目前唯一有中英文"));

	}
}