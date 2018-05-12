package com.wz.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

/**
 * html 解析
 * 
 * @author weiun
 *
 */
public class HtmlParser {

	public static JSONObject parserToBankCard(String html, String number) {
		JSONObject card = new JSONObject();
		// 银行图标
		int start = html.indexOf("/image/");
		int end = html.indexOf("\" alt=\"");
		if (start > 0 && end > start) {
			String icon = html.substring(start, end);
			icon = String.format("http://www.guabu.com%s", icon);
			card.put("icon", icon);
		}
		// 截取主要内容
		start = html.indexOf("归属地：");
		end = html.lastIndexOf("银行官方网址：");
		if (start > 0 && end > start) {
			html = html.substring(start, end);
		} else {
			return card;
		}
		// 服务电话
		String numbers = html.replace(number, "");
		Pattern pat = Pattern.compile("[0-9]{4,}");
		Matcher mat = pat.matcher(numbers);
		if (mat.find()) {
			String service_number = mat.group(0);
			card.put("service_number", service_number);
		}
		// 归属地
		String attribution = getItemContent(html, "归属地：");
		card.put("attribution", attribution);
		// 银行卡种类：
		String card_name = getItemContent(html, "银行卡种类：");
		if(card_name.equals("-")){
			card_name = "";
		}
		if (card_name.length() > 0) {
			String[] split = card_name.split("-");
			String bank_name = split.length> 0 ? split[0].trim() : "";
			// 银行加密
			card.put("bank_name", bank_name);
		}
		card.put("card_name", card_name);
		return card;
	}

	public static String getItemContent(String content, String flag) {
		int start = content.indexOf(flag);
		Pattern pat = Pattern.compile("<td class=\"STYLE2\">.*[\t|\r|\n]*</td>");
		Matcher mat = pat.matcher(content.substring(start));
		if (mat.find()) {
			String str = mat.group(0);
			str = str.replaceAll("<td class=\"STYLE2\">|</td>|\t|\r|\n", "").trim();
			str = str.replaceAll("<a .*>|</a>", "").trim();
			return str;
		}
		return null;
	}

}
