package com.wz.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

public class GeocodingAPIUtils {

	private static String app_key = "fKuOlQ09atEf3H3naptUmLOq";

	private static String httpUrl = "http://api.map.baidu.com/geocoder/v2/";

	/**
	 * 根据地址获取GPS数据
	 * 
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static JSONObject geocodingGetGPS(String address, String city) {
		try {
			BufferedReader reader = null;
			StringBuffer sbf = new StringBuffer();
			StringBuilder sb = new StringBuilder(httpUrl);
			sb.append("?output=json&ak=").append(app_key).append("&address=").append(URLEncoder.encode(address, "UTF-8")).append("&city=")
					.append(URLEncoder.encode(city, "UTF-8"));
			URL url = new URL(sb.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			JSONObject json = JSONObject.fromObject(sbf.toString());
			if (json.getInt("status") == 0) {
				return json.getJSONObject("result");
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static JSONObject geocodingGetAddress(String lat, String lng) {
		try {
			BufferedReader reader = null;
			StringBuffer sbf = new StringBuffer();
			StringBuilder sb = new StringBuilder(httpUrl);
			sb.append("?pois=0&output=json&ak=").append(app_key).append("&location=").append(lat).append(",").append(lng);
			URL url = new URL(sb.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			JSONObject json = JSONObject.fromObject(sbf.toString());
			if (json.getInt("status") == 0) {
				return json.getJSONObject("result");
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	public static void main(String[] args) {
		/*
		 * String city = "北京市";// "上海市"; String cityCode = "010" ;// "021";
		 * 
		 * 
		 * 
		 * Map<String,Map> queryCBD_INFO =
		 * JDBCSupportUtils.queryCBD_INFO(cityCode); int i=0; for
		 * (Map.Entry<String, Map> entry : queryCBD_INFO.entrySet()) { Map
		 * map=entry.getValue(); int
		 * belongId=Integer.parseInt(map.get("belongId").toString()); String
		 * name
		 * =belongId!=0?queryCBD_INFO.get(String.valueOf(belongId)).get("name"
		 * ).toString()
		 * +" "+map.get("name").toString():map.get("name").toString();
		 * JSONObject json = geocodingGetGPS( name , city); if(json!=null){
		 * JDBCSupportUtils
		 * .updateCBD_INFO(Integer.parseInt(map.get("id").toString()),
		 * json.getJSONObject("location").getString("lat"),
		 * json.getJSONObject("location").getString("lng")); i++; } }
		 * System.out.println("========"+i);
		 */
		// System.out.println(geocodingGetGPS("虹口区", "上海"));

		// List<Map> queryTestInfo = JDBCSupportUtils.queryTestInfo();

		// for (Map map : queryTestInfo) {
		// map.put("address", geocodingGetAddress(map.get("lat").toString(),
		// map.get("lng").toString()).get("formatted_address"));
		// //JDBCSupportUtils.update(map);
		// System.out.println(map);
		// }

	}

}
