package com.wz.common.utils;

import net.sf.json.JSONObject;

/**
 * GPS 计算
 * @author Johnson.Jia
 * @date 2015年12月2日 下午4:30:49
 */
public class GPSDistance{
	
	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1  	经度
	 * @param lat1		纬度
	 * @param lng2		经度
	 * @param lat2		纬度
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	/**
	 * 根据经纬度 和距离计算 经纬度范围
	 * @author Johnson.Jia
	 * @date 2015年8月13日 下午6:26:52
	 * @param json  经纬度坐标  {"lng":121.507722,"lat":31.280661}
	 * @param distance  计算相距范围
	 * @return maxLng 最大经度     minLng 最小经度   maxLat 最大纬度    minLat 最小纬度  
	 */ 
	public static JSONObject GetLongitudes(JSONObject json, double distance){
		double lng1=json.getDouble("lng");
		double lat1=json.getDouble("lat");
		double range = 180 / Math.PI * distance /EARTH_RADIUS ;     //里面的 1 就代表搜索 1m 之内，单位m  
		double lngR = range / Math.cos(lat1 * Math.PI / 180);  
		double maxLat = lat1 + range;         //最大纬度  
		double minLat = lat1 - range;         //最小纬度  
		double maxLng = lng1 + lngR;          //最大经度  
		double minLng = lng1 - lngR;          //最小经度  
		JSONObject resutJson=new JSONObject();
		resutJson.put("maxLng", Math.round(maxLng*1000000)/1000000.0  );
		resutJson.put("minLng", Math.round(minLng*1000000)/1000000.0  );
		resutJson.put("maxLat", Math.round(maxLat*1000000)/1000000.0  );
		resutJson.put("minLat", Math.round(minLat*1000000)/1000000.0  );
		return resutJson;
	}
	
	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * @param json1   起始坐标 {"lng":121.507722,"lat":31.280661}
	 * @param json2   目的坐标 {"lng":121.507722,"lat":31.280661}
	 * @return
	 */
	public static double GetDistance(JSONObject json1,JSONObject json2) {
		return GetDistance(json1.getDouble("lng"), json1.getDouble("lat"), json2.getDouble("lng"),  json2.getDouble("lat"));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//GPS=["{lat:31.281947,lng:121.491931}"]
		double distance = GetDistance(121.491931,31.281947,121.5131140000,31.2555720000);
		
		System.out.println("Distance is:" + distance);
		
		System.out.println(GetLongitudes(JSONObject.fromObject("{\"lng\":121.486226,\"lat\":31.27667}"), 2000));

	}

}
