package com.wz.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

public class JDBCSupportUtils {

	/**
	 * @param args
	 */
	// 驱动程序就是之前在classpath中配置的JDBC的驱动程序的JAR 包中
	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	// 连接地址是由各个数据库生产商单独提供的，所以需要单独记住
	public static final String DBURL = "jdbc:mysql://192.168.1.125:3306/mgnewerp?useUnicode=true&characterEncoding=utf8";
	// "jdbc:oracle:thin:@localhost:1521:CIG";
	// 连接数据库的用户名
	public static final String DBUSER = "root";
	// 连接数据库的密码
	public static final String DBPASS = "sysdba";

	public static Connection getConnection() {
		Connection con = null; // 表示数据库的连接对象
		try {
			Class.forName(DBDRIVER);
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;

	}

	public static Map<Integer, JSONObject> queryList() {
		StringBuilder str = new StringBuilder();
		str.append("SELECT  id,`name`,pinyin FROM `base_biz_breed`;");
		Map<Integer, JSONObject> result = new HashMap<Integer, JSONObject>();
		PreparedStatement psmt = null;
		Connection conn = getConnection();
		try {
			psmt = conn.prepareStatement(str.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("name", rs.getString("name"));
				json.put("pinyin", rs.getString("pinyin"));
				result.put(rs.getInt("id"), json);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void updateInfo(Map<Integer, JSONObject> queryList) throws Exception {
		Iterator<Entry<Integer, JSONObject>> iterator = queryList.entrySet().iterator();
		int num = 0;
		PreparedStatement psmt = null;
		Connection conn = getConnection();
		try {
			while (iterator.hasNext()) {
				Map.Entry<java.lang.Integer, net.sf.json.JSONObject> entry = (Map.Entry<java.lang.Integer, net.sf.json.JSONObject>) iterator.next();
				JSONObject value = entry.getValue();
				psmt = conn.prepareStatement("update  `base_biz_breed`  set pinyin = '" + value.optString("pinyin") + "' , name = '"
						+ value.optString("name") + "' where id = '" + entry.getKey() + "' ;");
				int i = psmt.executeUpdate();
				num = num + i;
				System.out.println("更新数据  ==== " + i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("更新数据总数量  ==== " + num);

	}

	public static void main(String[] args) throws Exception {
		Map<Integer, JSONObject> queryList = queryList();
		Iterator<Entry<Integer, JSONObject>> iterator = queryList.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<java.lang.Integer, net.sf.json.JSONObject> entry = (Map.Entry<java.lang.Integer, net.sf.json.JSONObject>) iterator.next();
			JSONObject value = entry.getValue();
			String name = value.optString("name").trim().replaceAll(" ", "").replaceAll(" ", "");
			if (name.contains("白胡椒")) {
				System.out.println(name);
			}
			char[] charArray = name.toCharArray();
			String namePinyin = "";
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				String string = java.lang.Character.toString(c);
				if (ChineseToEnglish.isCheckString(string)) {
					String pingYin = ChineseToEnglish.getPingYin(string);
					namePinyin = namePinyin + pingYin.substring(0, 1).toUpperCase() + pingYin.substring(1, pingYin.length());
				} else {
					namePinyin = namePinyin + string;
				}
			}
			value.put("pinyin", namePinyin.trim());
			value.put("name", name);
		}
		updateInfo(queryList);

	}

}
