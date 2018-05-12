package com.wz.common.utils;

import java.util.Calendar;
import java.util.HashMap;

public class ChineseCalendar {

	public ChineseCalendar() {

		gregorianYear = 1901;
		gregorianMonth = 1;
		gregorianDay = 1;
		chineseYear = 4598;
		chineseMonth = 11;
		chineseDay = 11;
		gregorianDayOfYear = 1;
		gregorianDayOfWeek = 1;
		gregorianDayOfWeekInMonth = 1;

		solarFestaMap = new HashMap<String, String>();
		lunarFestaMap = new HashMap<String, String>();

		solarFestaMap.put("1\u67081\u65E5", "\u5143\u65E6");
		solarFestaMap.put("2\u67087\u65E5", "\u4E8C.\u4E03\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("2\u670814\u65E5", "\u60C5\u4EBA\u8282");
		solarFestaMap.put("2\u670824\u65E5", "\u4E16\u754C\u7B2C\u4E09\u9752\u5E74\u65E5");
		solarFestaMap.put("3\u67081\u65E5", "\u56FD\u9645\u6D77\u8C79\u8282");
		solarFestaMap.put("3\u67085\u65E5", "\u5B66\u4E60\u96F7\u950B\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("3\u67088\u65E5", "'\u4E09\uFF0E\u516B'\u56FD\u9645\u5987\u5973\u8282");
		solarFestaMap.put("3\u670812\u65E5", "\u4E2D\u56FD\u690D\u6811\u8282");
		solarFestaMap.put("3\u670814\u65E5", "\u56FD\u9645\u8B66\u5BDF\u65E5");
		solarFestaMap.put("3\u670815\u65E5", "\u56FD\u9645\u6D88\u8D39\u8005\u6743\u76CA\u65E5");
		solarFestaMap.put("3\u670821\u65E5",
				"\u4E16\u754C\u68EE\u6797\u65E5,\u6D88\u9664\u79CD\u65CF\u6B67\u89C6\u65E5");
		solarFestaMap.put("3\u670822\u65E5", "\u4E16\u754C\u6C34\u65E5");
		solarFestaMap.put("3\u670823\u65E5", "\u4E16\u754C\u6C14\u8C61\u65E5");
		solarFestaMap.put("4\u67081\u65E5", "\u611A\u4EBA\u8282");
		solarFestaMap.put("4\u67085\u65E5", "\u6E05\u660E\u8282");
		solarFestaMap.put("4\u67087\u65E5", "\u4E16\u754C\u536B\u751F\u65E5");
		solarFestaMap.put("4\u670822\u65E5", "\u4E16\u754C\u5730\u7403\u65E5");
		solarFestaMap.put("4\u670824\u65E5", "\u4E16\u754C\u4E16\u754C\u9752\u5E74\u56E2\u7ED3\u65E5");
		solarFestaMap.put("4\u670825\u65E5", "\u4E16\u754C\u513F\u7AE5\u65E5");
		solarFestaMap.put("5\u67081\u65E5", "\u56FD\u9645\u52B3\u52A8\u8282");
		solarFestaMap.put("5\u67084\u65E5", "\u4E2D\u56FD\u9752\u5E74\u8282");
		solarFestaMap.put("5\u67088\u65E5", "\u4E16\u754C\u7EA2\u5341\u5B57\u65E5");
		solarFestaMap.put("5\u670812\u65E5", "\u56FD\u9645\u62A4\u58EB\u8282");
		solarFestaMap.put("5\u670815\u65E5", "\u56FD\u9645\u5BB6\u5EAD\u65E5");
		solarFestaMap.put("5\u670817\u65E5", "\u4E16\u754C\u7535\u4FE1\u65E5");
		solarFestaMap.put("5\u670819\u65E5", "\u5168\u56FD\u52A9\u6B8B\u65E5");
		solarFestaMap.put("5\u670830\u65E5", "\u4E2D\u56FD'\u4E94.\u5345'\u8FD0\u52A8\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("5\u670831\u65E5", "\u4E16\u754C\u65E0\u70DF\u65E5");
		solarFestaMap.put("6\u67081\u65E5",
				"\u56FD\u9645\u513F\u7AE5\u8282,\u56FD\u9645\u513F\u7AE5\u7535\u5F71\u8282");
		solarFestaMap.put("6\u67084\u65E5", "\u56FD\u9645\u65E5");
		solarFestaMap.put("6\u67085\u65E5", "\u56FD\u9645\u73AF\u5883\u65E5");
		solarFestaMap.put("6\u670823\u65E5", "\u56FD\u9645\u5965\u6797\u5339\u514B\u65E5");
		solarFestaMap.put("6\u670825\u65E5", "\u4E2D\u56FD\u571F\u5730\u65E5");
		solarFestaMap.put("6\u670827\u65E5", "\u56FD\u9645\u7981\u6BD2\u65E5");
		solarFestaMap.put("7\u67081\u65E5",
				"\u4E2D\u56FD\u5171\u4EA7\u515A\u6210\u7ACB\u7EAA\u5FF5\u65E5,\u56FD\u9645\u5EFA\u7B51\u65E5");
		solarFestaMap.put("7\u67083\u65E5", "\u56FD\u9645\u5408\u4F5C\u8282");
		solarFestaMap.put("7\u67087\u65E5", "\u6297\u6218\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("7\u670811\u65E5", "\u4E16\u754C\u4EBA\u53E3\u65E5");
		solarFestaMap.put("8\u67081\u65E5", "\u4E2D\u56FD\u4EBA\u6C11\u89E3\u653E\u519B\u5EFA\u519B\u8282");
		solarFestaMap.put("9\u67088\u65E5", "\u56FD\u9645\u65B0\u95FB\u5DE5\u4F5C\u8005\u65E5,\u626B\u76F2\u65E5");
		solarFestaMap.put("9\u67089\u65E5", "\u6BDB\u6CFD\u4E1C\u901D\u4E16\u7EAA\u5FF5");
		solarFestaMap.put("9\u670810\u65E5", "\u4E2D\u56FD\u6559\u5E08\u8282");
		solarFestaMap.put("9\u670820\u65E5", "\u56FD\u9645\u7231\u7259\u65E5");
		solarFestaMap.put("9\u670827\u65E5", "\u5B54\u5B50\u8BDE\u8FB0");
		solarFestaMap.put("10\u67081\u65E5", "\u4E2D\u534E\u4EBA\u6C11\u5171\u548C\u56FD\u56FD\u5E86\u8282");
		solarFestaMap.put("10\u67082\u65E5", "\u56FD\u9645\u548C\u5E73\u6597\u4E89\u65E5");
		solarFestaMap.put("10\u67084\u65E5", "\u4E16\u754C\u52A8\u7269\u65E5");
		solarFestaMap.put("10\u67089\u65E5", "\u4E16\u754C\u90AE\u653F\u65E5");
		solarFestaMap.put("10\u670814\u65E5", "\u4E16\u754C\u6807\u51C6\u65E5");
		solarFestaMap.put("10\u670815\u65E5", "\u4E16\u754C\u76F2\u4EBA\u65E5");
		solarFestaMap.put("10\u670816\u65E5", "\u4E16\u754C\u7CAE\u98DF\u65E5");
		solarFestaMap.put("10\u670824\u65E5", "\u8054\u5408\u56FD\u65E5");
		solarFestaMap.put("10\u670831\u65E5", "\u4E16\u754C\u52E4\u4FED\u65E5,\u4E07\u5723\u8282");
		solarFestaMap.put("11\u67088\u65E5", "\u4E2D\u56FD\u8BB0\u8005\u65E5");
		solarFestaMap.put("11\u670812\u65E5", "\u5B59\u4E2D\u5C71\u8BDE\u8FB0");
		solarFestaMap.put("11\u670817\u65E5", "\u56FD\u9645\u5B66\u751F\u65E5");
		solarFestaMap.put("12\u67081\u65E5", "\u4E16\u754C\u7231\u6ECB\u75C5\u65E5");
		solarFestaMap.put("12\u67083\u65E5", "\u4E16\u754C\u6B8B\u75BE\u4EBA\u65E5");
		solarFestaMap.put("12\u67089\u65E5", "\u4E16\u754C\u8DB3\u7403\u65E5");
		solarFestaMap.put("12\u67085\u65E5",
				"\u793E\u4F1A\u7ECF\u6D4E\u53D1\u5C55\u56FD\u9645\u5FD7\u613F\u4EBA\u5458\u65E5");
		solarFestaMap.put("12\u670810\u65E5", "\u4E16\u754C\u4EBA\u6743\u65E5,\u8BFA\u8D1D\u5C14\u65E5");
		solarFestaMap.put("12\u670820\u65E5", "\u6FB3\u95E8\u56DE\u5F52");
		solarFestaMap.put("12\u670822\u65E5", "\u51AC\u81F3");
		solarFestaMap.put("12\u670824\u65E5", "\u5E73\u5B89\u591C");
		solarFestaMap.put("12\u670825\u65E5", "\u5723\u8BDE\u8282");
		solarFestaMap.put("12\u670826\u65E5", "\u6BDB\u6CFD\u4E1C\u8BDE\u8FB0");
		lunarFestaMap.put("12\u67088\u65E5", "\u814A\u516B\u8282,\u51AC\u81F3\u8282");
		lunarFestaMap.put("12\u670829\u65E5", "\u9664\u5915");
		lunarFestaMap.put("12\u670830\u65E5", "\u9664\u5915");
		lunarFestaMap.put("1\u67081\u65E5", "\u6625\u8282");
		lunarFestaMap.put("1\u670815\u65E5", "\u5143\u5BB5\u8282");
		lunarFestaMap.put("1\u670816\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670817\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670818\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670819\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670820\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670825\u65E5", "\u586B\u4ED3\u8282");
		lunarFestaMap.put("1\u670829\u65E5", "\u9001\u7A77\u65E5");
		lunarFestaMap.put("2\u67081\u65E5", "\u7476\u65CF\u5FCC\u9E1F\u8282");
		lunarFestaMap.put("2\u67082\u65E5", "\u6625\u9F99\u8282,\u7572\u65CF\u4F1A\u4EB2\u8282");
		lunarFestaMap.put("2\u67088\u65E5", "\u5088\u5088\u65CF\u5200\u6746\u8282");
		lunarFestaMap.put("3\u670815\u65E5", "\u4F64\u65CF\u64AD\u79CD\u8282,\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670816\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670817\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670818\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670819\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670820\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670821\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670822\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670823\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670824\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670825\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("4\u67088\u65E5", "\u725B\u738B\u8BDE");
		lunarFestaMap.put("4\u670818\u65E5", "\u9521\u4F2F\u65CF\u897F\u8FC1\u8282");
		lunarFestaMap.put("5\u67085\u65E5", "\u7AEF\u5348\u8282");
		lunarFestaMap.put("5\u670813\u65E5", "\u963F\u660C\u65CF\u6CFC\u6C34\u8282");
		lunarFestaMap.put("5\u670822\u65E5", "\u9102\u6E29\u514B\u65CF\u7C73\u9614\u9C81\u8282");
		lunarFestaMap.put("5\u670829\u65E5", "\u7476\u65CF\u8FBE\u52AA\u8282");
		lunarFestaMap.put("6\u67086\u65E5", "\u58EE\u65CF\u796D\u7530\u8282,\u7476\u65CF\u5C1D\u65B0\u8282");
		lunarFestaMap.put("6\u670824\u65E5", "\u706B\u628A\u8282");
		lunarFestaMap.put("7\u67087\u65E5", "\u5973\u513F\u8282");
		lunarFestaMap.put("7\u670813\u65E5", "\u4F97\u65CF\u5403\u65B0\u8282");
		lunarFestaMap.put("7\u670815\u65E5", "\u76C2\u5170\u76C6\u4F1A\u3001\u666E\u7C73\u65CF\u8F6C\u5C71\u4F1A");
		lunarFestaMap.put("8\u670815\u65E5", "\u4E2D\u79CB\u8282");
		lunarFestaMap.put("9\u67089\u65E5", "\u91CD\u9633\u8282");
		lunarFestaMap.put("10\u67081\u65E5", "\u796D\u7956\u8282");
		lunarFestaMap.put("10\u670816\u65E5", "\u7476\u65CF\u76D8\u738B\u8282");

		Calendar calendar = Calendar.getInstance();
		gregorianYear = calendar.get(1);
		gregorianMonth = calendar.get(2) + 1;
		gregorianDay = calendar.get(5);
		init();
	}

	public ChineseCalendar(int gregorianYear, int gregorianMonth, int gregorianDay) throws Exception {

		this.gregorianYear = 1901;
		this.gregorianMonth = 1;
		this.gregorianDay = 1;
		chineseYear = 4598;
		chineseMonth = 11;
		chineseDay = 11;
		gregorianDayOfYear = 1;
		gregorianDayOfWeek = 1;
		gregorianDayOfWeekInMonth = 1;
		solarFestaMap = new HashMap<String, String>();
		lunarFestaMap = new HashMap<String, String>();
		solarFestaMap.put("1\u67081\u65E5", "\u5143\u65E6");
		solarFestaMap.put("2\u67087\u65E5", "\u4E8C.\u4E03\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("2\u670814\u65E5", "\u60C5\u4EBA\u8282");
		solarFestaMap.put("2\u670824\u65E5", "\u4E16\u754C\u7B2C\u4E09\u9752\u5E74\u65E5");
		solarFestaMap.put("3\u67081\u65E5", "\u56FD\u9645\u6D77\u8C79\u8282");
		solarFestaMap.put("3\u67085\u65E5", "\u5B66\u4E60\u96F7\u950B\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("3\u67088\u65E5", "'\u4E09\uFF0E\u516B'\u56FD\u9645\u5987\u5973\u8282");
		solarFestaMap.put("3\u670812\u65E5", "\u4E2D\u56FD\u690D\u6811\u8282");
		solarFestaMap.put("3\u670814\u65E5", "\u56FD\u9645\u8B66\u5BDF\u65E5");
		solarFestaMap.put("3\u670815\u65E5", "\u56FD\u9645\u6D88\u8D39\u8005\u6743\u76CA\u65E5");
		solarFestaMap.put("3\u670821\u65E5",
				"\u4E16\u754C\u68EE\u6797\u65E5,\u6D88\u9664\u79CD\u65CF\u6B67\u89C6\u65E5");
		solarFestaMap.put("3\u670822\u65E5", "\u4E16\u754C\u6C34\u65E5");
		solarFestaMap.put("3\u670823\u65E5", "\u4E16\u754C\u6C14\u8C61\u65E5");
		solarFestaMap.put("4\u67081\u65E5", "\u611A\u4EBA\u8282");
		solarFestaMap.put("4\u67085\u65E5", "\u6E05\u660E\u8282");
		solarFestaMap.put("4\u67087\u65E5", "\u4E16\u754C\u536B\u751F\u65E5");
		solarFestaMap.put("4\u670822\u65E5", "\u4E16\u754C\u5730\u7403\u65E5");
		solarFestaMap.put("4\u670824\u65E5", "\u4E16\u754C\u4E16\u754C\u9752\u5E74\u56E2\u7ED3\u65E5");
		solarFestaMap.put("4\u670825\u65E5", "\u4E16\u754C\u513F\u7AE5\u65E5");
		solarFestaMap.put("5\u67081\u65E5", "\u56FD\u9645\u52B3\u52A8\u8282");
		solarFestaMap.put("5\u67084\u65E5", "\u4E2D\u56FD\u9752\u5E74\u8282");
		solarFestaMap.put("5\u67088\u65E5", "\u4E16\u754C\u7EA2\u5341\u5B57\u65E5");
		solarFestaMap.put("5\u670812\u65E5", "\u56FD\u9645\u62A4\u58EB\u8282");
		solarFestaMap.put("5\u670815\u65E5", "\u56FD\u9645\u5BB6\u5EAD\u65E5");
		solarFestaMap.put("5\u670817\u65E5", "\u4E16\u754C\u7535\u4FE1\u65E5");
		solarFestaMap.put("5\u670819\u65E5", "\u5168\u56FD\u52A9\u6B8B\u65E5");
		solarFestaMap.put("5\u670830\u65E5", "\u4E2D\u56FD'\u4E94.\u5345'\u8FD0\u52A8\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("5\u670831\u65E5", "\u4E16\u754C\u65E0\u70DF\u65E5");
		solarFestaMap.put("6\u67081\u65E5",
				"\u56FD\u9645\u513F\u7AE5\u8282,\u56FD\u9645\u513F\u7AE5\u7535\u5F71\u8282");
		solarFestaMap.put("6\u67084\u65E5", "\u56FD\u9645\u65E5");
		solarFestaMap.put("6\u67085\u65E5", "\u56FD\u9645\u73AF\u5883\u65E5");
		solarFestaMap.put("6\u670823\u65E5", "\u56FD\u9645\u5965\u6797\u5339\u514B\u65E5");
		solarFestaMap.put("6\u670825\u65E5", "\u4E2D\u56FD\u571F\u5730\u65E5");
		solarFestaMap.put("6\u670827\u65E5", "\u56FD\u9645\u7981\u6BD2\u65E5");
		solarFestaMap.put("7\u67081\u65E5",
				"\u4E2D\u56FD\u5171\u4EA7\u515A\u6210\u7ACB\u7EAA\u5FF5\u65E5,\u56FD\u9645\u5EFA\u7B51\u65E5");
		solarFestaMap.put("7\u67083\u65E5", "\u56FD\u9645\u5408\u4F5C\u8282");
		solarFestaMap.put("7\u67087\u65E5", "\u6297\u6218\u7EAA\u5FF5\u65E5");
		solarFestaMap.put("7\u670811\u65E5", "\u4E16\u754C\u4EBA\u53E3\u65E5");
		solarFestaMap.put("8\u67081\u65E5", "\u4E2D\u56FD\u4EBA\u6C11\u89E3\u653E\u519B\u5EFA\u519B\u8282");
		solarFestaMap.put("9\u67088\u65E5", "\u56FD\u9645\u65B0\u95FB\u5DE5\u4F5C\u8005\u65E5,\u626B\u76F2\u65E5");
		solarFestaMap.put("9\u67089\u65E5", "\u6BDB\u6CFD\u4E1C\u901D\u4E16\u7EAA\u5FF5");
		solarFestaMap.put("9\u670810\u65E5", "\u4E2D\u56FD\u6559\u5E08\u8282");
		solarFestaMap.put("9\u670820\u65E5", "\u56FD\u9645\u7231\u7259\u65E5");
		solarFestaMap.put("9\u670827\u65E5", "\u5B54\u5B50\u8BDE\u8FB0");
		solarFestaMap.put("10\u67081\u65E5", "\u4E2D\u534E\u4EBA\u6C11\u5171\u548C\u56FD\u56FD\u5E86\u8282");
		solarFestaMap.put("10\u67082\u65E5", "\u56FD\u9645\u548C\u5E73\u6597\u4E89\u65E5");
		solarFestaMap.put("10\u67084\u65E5", "\u4E16\u754C\u52A8\u7269\u65E5");
		solarFestaMap.put("10\u67089\u65E5", "\u4E16\u754C\u90AE\u653F\u65E5");
		solarFestaMap.put("10\u670814\u65E5", "\u4E16\u754C\u6807\u51C6\u65E5");
		solarFestaMap.put("10\u670815\u65E5", "\u4E16\u754C\u76F2\u4EBA\u65E5");
		solarFestaMap.put("10\u670816\u65E5", "\u4E16\u754C\u7CAE\u98DF\u65E5");
		solarFestaMap.put("10\u670824\u65E5", "\u8054\u5408\u56FD\u65E5");
		solarFestaMap.put("10\u670831\u65E5", "\u4E16\u754C\u52E4\u4FED\u65E5,\u4E07\u5723\u8282");
		solarFestaMap.put("11\u67088\u65E5", "\u4E2D\u56FD\u8BB0\u8005\u65E5");
		solarFestaMap.put("11\u670812\u65E5", "\u5B59\u4E2D\u5C71\u8BDE\u8FB0");
		solarFestaMap.put("11\u670817\u65E5", "\u56FD\u9645\u5B66\u751F\u65E5");
		solarFestaMap.put("12\u67081\u65E5", "\u4E16\u754C\u7231\u6ECB\u75C5\u65E5");
		solarFestaMap.put("12\u67083\u65E5", "\u4E16\u754C\u6B8B\u75BE\u4EBA\u65E5");
		solarFestaMap.put("12\u67089\u65E5", "\u4E16\u754C\u8DB3\u7403\u65E5");
		solarFestaMap.put("12\u67085\u65E5",
				"\u793E\u4F1A\u7ECF\u6D4E\u53D1\u5C55\u56FD\u9645\u5FD7\u613F\u4EBA\u5458\u65E5");
		solarFestaMap.put("12\u670810\u65E5", "\u4E16\u754C\u4EBA\u6743\u65E5,\u8BFA\u8D1D\u5C14\u65E5");
		solarFestaMap.put("12\u670820\u65E5", "\u6FB3\u95E8\u56DE\u5F52");
		solarFestaMap.put("12\u670822\u65E5", "\u51AC\u81F3");
		solarFestaMap.put("12\u670824\u65E5", "\u5E73\u5B89\u591C");
		solarFestaMap.put("12\u670825\u65E5", "\u5723\u8BDE\u8282");
		solarFestaMap.put("12\u670826\u65E5", "\u6BDB\u6CFD\u4E1C\u8BDE\u8FB0");
		lunarFestaMap.put("12\u67088\u65E5", "\u814A\u516B\u8282,\u51AC\u81F3\u8282");
		lunarFestaMap.put("12\u670829\u65E5", "\u9664\u5915");
		lunarFestaMap.put("12\u670830\u65E5", "\u9664\u5915");
		lunarFestaMap.put("1\u67081\u65E5", "\u6625\u8282");
		lunarFestaMap.put("1\u670815\u65E5", "\u5143\u5BB5\u8282");
		lunarFestaMap.put("1\u670816\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670817\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670818\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670819\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670820\u65E5", "\u4F97\u65CF\u82A6\u7B19\u8282");
		lunarFestaMap.put("1\u670825\u65E5", "\u586B\u4ED3\u8282");
		lunarFestaMap.put("1\u670829\u65E5", "\u9001\u7A77\u65E5");
		lunarFestaMap.put("2\u67081\u65E5", "\u7476\u65CF\u5FCC\u9E1F\u8282");
		lunarFestaMap.put("2\u67082\u65E5", "\u6625\u9F99\u8282,\u7572\u65CF\u4F1A\u4EB2\u8282");
		lunarFestaMap.put("2\u67088\u65E5", "\u5088\u5088\u65CF\u5200\u6746\u8282");
		lunarFestaMap.put("3\u670815\u65E5", "\u4F64\u65CF\u64AD\u79CD\u8282,\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670816\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670817\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670818\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670819\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670820\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670821\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670822\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670823\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670824\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("3\u670825\u65E5", "\u767D\u65CF\u4E09\u6708\u8857");
		lunarFestaMap.put("4\u67088\u65E5", "\u725B\u738B\u8BDE");
		lunarFestaMap.put("4\u670818\u65E5", "\u9521\u4F2F\u65CF\u897F\u8FC1\u8282");
		lunarFestaMap.put("5\u67085\u65E5", "\u7AEF\u5348\u8282");
		lunarFestaMap.put("5\u670813\u65E5", "\u963F\u660C\u65CF\u6CFC\u6C34\u8282");
		lunarFestaMap.put("5\u670822\u65E5", "\u9102\u6E29\u514B\u65CF\u7C73\u9614\u9C81\u8282");
		lunarFestaMap.put("5\u670829\u65E5", "\u7476\u65CF\u8FBE\u52AA\u8282");
		lunarFestaMap.put("6\u67086\u65E5", "\u58EE\u65CF\u796D\u7530\u8282,\u7476\u65CF\u5C1D\u65B0\u8282");
		lunarFestaMap.put("6\u670824\u65E5", "\u706B\u628A\u8282");
		lunarFestaMap.put("7\u67087\u65E5", "\u5973\u513F\u8282");
		lunarFestaMap.put("7\u670813\u65E5", "\u4F97\u65CF\u5403\u65B0\u8282");
		lunarFestaMap.put("7\u670815\u65E5", "\u76C2\u5170\u76C6\u4F1A\u3001\u666E\u7C73\u65CF\u8F6C\u5C71\u4F1A");
		lunarFestaMap.put("8\u670815\u65E5", "\u4E2D\u79CB\u8282");
		lunarFestaMap.put("9\u67089\u65E5", "\u91CD\u9633\u8282");
		lunarFestaMap.put("10\u67081\u65E5", "\u796D\u7956\u8282");
		lunarFestaMap.put("10\u670816\u65E5", "\u7476\u65CF\u76D8\u738B\u8282");

		if (gregorianYear > 2100 || gregorianYear < 1901)
			throw new Exception("Year > 2100 or Year < 1901");

		if (gregorianMonth > 12 || gregorianMonth < 1)
			throw new Exception("Month > 12 or Month < 1");

		if (gregorianDay > 31 || gregorianDay < 1) {
			throw new Exception("Day > 31 or Day < 1");
		} else {
			this.gregorianYear = gregorianYear;
			this.gregorianMonth = gregorianMonth;
			this.gregorianDay = gregorianDay;
			init();
			return;
		}
	}

	private void init() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(gregorianYear, gregorianMonth - 1, gregorianDay);
		gregorianDayOfYear = calendar.get(6);
		gregorianDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		gregorianDayOfWeekInMonth = calendar.get(8);
		sectionalTerm = sectionalTerm(gregorianYear, gregorianMonth);
		principleTerm = principleTerm(gregorianYear, gregorianMonth);
		computeChineseFields();
	}

	private void computeChineseFields() {
		int startYear = 1901;
		int startMonth = 1;
		int startDate = 1;
		chineseYear = 4597;
		chineseMonth = 11;
		chineseDay = 11;
		if (gregorianYear >= 2000) {
			startYear = 2000;
			startMonth = 1;
			startDate = 1;
			chineseYear = 4696;
			chineseMonth = 11;
			chineseDay = 25;
		}
		int daysDiff = 0;
		for (int i = startYear; i < gregorianYear; i++) {
			daysDiff += 365;
			if (isGregorianLeapYear(i))
				daysDiff++;
		}

		for (int i = startMonth; i < gregorianMonth; i++)
			daysDiff += daysInGregorianMonth(gregorianYear, i);

		daysDiff += gregorianDay - startDate;
		chineseDay += daysDiff;
		int lastDate = daysInChineseMonth(chineseYear, chineseMonth);
		for (int nextMonth = nextChineseMonth(chineseYear,
				chineseMonth); chineseDay > lastDate; nextMonth = nextChineseMonth(chineseYear, chineseMonth)) {
			if (Math.abs(nextMonth) < Math.abs(chineseMonth))
				chineseYear++;
			chineseMonth = nextMonth;
			chineseDay -= lastDate;
			lastDate = daysInChineseMonth(chineseYear, chineseMonth);
		}

	}

	public int daysInGregorianMonth(int year, int month) {
		int day = daysInGregorianMonth[month - 1];
		if (month == 2 && isGregorianLeapYear(year))
			day++;
		return day;
	}

	public int daysInChineseMonth(int year, int month) {
		int index = (year - 4597) + 0;
		int v = 0;
		int l = 0;
		int d = 30;
		if (1 <= month && month <= 8) {
			v = chineseMonths[2 * index];
			l = month - 1;
			if ((v >> l & 1) == 1)
				d = 29;
		} else if (9 <= month && month <= 12) {
			v = chineseMonths[2 * index + 1];
			l = month - 9;
			if ((v >> l & 1) == 1)
				d = 29;
		} else {
			v = chineseMonths[2 * index + 1];
			v = v >> 4 & 15;
			if (v != Math.abs(month)) {
				d = 0;
			} else {
				d = 29;
				for (int i = 0; i < bigLeapMonthYears.length; i++) {
					if (bigLeapMonthYears[i] != index)
						continue;
					d = 30;
					break;
				}

			}
		}
		return d;
	}

	public int nextChineseMonth(int year, int month) {
		int n = Math.abs(month) + 1;
		if (month > 0) {
			int index = (year - 4597) + 0;
			int v = chineseMonths[2 * index + 1];
			v = v >> 4 & 15;
			if (v == month)
				n = -month;
		}
		if (n == 13)
			n = 1;
		return n;
	}

	public boolean isGregorianLeapYear(int year) {
		boolean isLeap = false;
		if (year % 4 == 0)
			isLeap = true;
		if (year % 100 == 0)
			isLeap = false;
		if (year % 400 == 0)
			isLeap = true;
		return isLeap;
	}

	public boolean isGregorianLeapYear() {
		boolean isLeap = false;
		if (gregorianYear % 4 == 0)
			isLeap = true;
		if (gregorianYear % 100 == 0)
			isLeap = false;
		if (gregorianYear % 400 == 0)
			isLeap = true;
		return isLeap;
	}

	public String getChineseMonthStr() {
		String str = "";
		if (chineseMonth > 0)
			str = (new StringBuilder(String.valueOf(chineseMonthNames[chineseMonth - 1]))).append("\u6708").toString();
		else if (chineseMonth < 0)
			str = (new StringBuilder("\u95F0")).append(chineseMonthNames[-chineseMonth - 1]).append("\u6708")
					.toString();
		return str;
	}

	public String getChineseYearStr() {
		return (new StringBuilder(String.valueOf(stemNames[(chineseYear - 1) % 10])))
				.append(branchNames[(chineseYear - 1) % 12]).toString();
	}

	public String getChineseAnimalStr() {
		return animalNames[(chineseYear - 1) % 12];
	}

	public String getChineseDayStr() {
		String chineseTen[] = { "\u521D", "\u5341", "\u5EFF", "\u5345" };
		int n = chineseDay % 10 != 0 ? chineseDay % 10 - 1 : 9;
		if (chineseDay > 30)
			return "";
		if (chineseDay == 10)
			return "\u521D\u5341";
		else
			return (new StringBuilder(String.valueOf(chineseTen[chineseDay / 10]))).append(monthNames[n]).toString();
	}

	public String getTermStr() {
		String str = "";
		if (gregorianDay == sectionalTerm)
			str = sectionalTermNames[gregorianMonth - 1];
		else if (gregorianDay == principleTerm)
			str = principleTermNames[gregorianMonth - 1];
		return str;
	}

	private int sectionalTerm(int year, int month) {
		if (year < 1901 || year > 2100)
			return 0;
		int index = 0;
		int ry;
		for (ry = (year - 1901) + 1; ry >= sectionalTermYear[month - 1][index]; index++)
			;
		int term = sectionalTermMap[month - 1][4 * index + ry % 4];
		if (ry == 121 && month == 4)
			term = 5;
		if (ry == 132 && month == 4)
			term = 5;
		if (ry == 194 && month == 6)
			term = 6;
		return term;
	}

	private int principleTerm(int year, int month) {
		if (year < 1901 || year > 2100)
			return 0;
		int index = 0;
		int ry;
		for (ry = (year - 1901) + 1; ry >= principleTermYear[month - 1][index]; index++)
			;
		int term = principleTermMap[month - 1][4 * index + ry % 4];
		if (ry == 171 && month == 3)
			term = 21;
		if (ry == 181 && month == 5)
			term = 21;
		return term;
	}

	public String getLunarFestaStr() {
		String str = "";
		if (chineseDay == 29 && daysInChineseMonth(chineseYear, 12) == 30)
			str = "";
		else
			str = (new StringBuilder(String.valueOf(str)))
					.append(lunarFestaMap.get((new StringBuilder(String.valueOf(chineseMonth))).append("\u6708")
							.append(chineseDay).append("\u65E5").toString()) != null
									? (String) lunarFestaMap.get((new StringBuilder(String.valueOf(chineseMonth)))
											.append("\u6708").append(chineseDay).append("\u65E5").toString())
									: "")
					.toString();
		return str;
	}

	public String getSolarFestaStr() {
		String str = "";
		str = (new StringBuilder(String.valueOf(str)))
				.append(solarFestaMap.get((new StringBuilder(String.valueOf(gregorianMonth))).append("\u6708")
						.append(gregorianDay).append("\u65E5").toString()) != null
								? (String) solarFestaMap.get((new StringBuilder(String.valueOf(gregorianMonth)))
										.append("\u6708").append(gregorianDay).append("\u65E5").toString())
								: "")
				.toString();
		str = (new StringBuilder(String.valueOf(str))).append(getOtherFestaStr()).toString();
		return str;
	}

	private int getFirstWeekDay(int gregorianYear, int gregorianMonth, int wk) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(this.gregorianYear, this.gregorianMonth - 1, 1);
		int week = calendar.get(7);
		int firstWeekDay = 0;
		if (week == wk)
			firstWeekDay = 1;
		else
			firstWeekDay = 9 - week;
		return firstWeekDay;
	}

	private int getLastWeekDay(int year, int month, int wk) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int maxDay = calendar.getMaximum(5);
		int week = calendar.get(7);
		int lastWeekDay = 0;
		if (week == wk)
			lastWeekDay = maxDay;
		else
			lastWeekDay = (maxDay - week) + 1;
		return lastWeekDay;
	}

	public String getOtherFestaStr() {
		String str = "";
		switch (gregorianMonth) {
		case 7: // '\007'
		case 8: // '\b'
		default:
			break;

		case 5: // '\005'
		{
			int tempDay = getFirstWeekDay(gregorianYear, gregorianMonth, 1) + 7;
			if (gregorianDay == tempDay)
				str = (new StringBuilder(String.valueOf(str))).append("\u56FD\u9645\u6BCD\u4EB2\u8282").toString();
			break;
		}

		case 6: // '\006'
		{
			int tempDay = getFirstWeekDay(gregorianYear, gregorianMonth, 1) + 14;
			if (gregorianDay == tempDay)
				str = (new StringBuilder(String.valueOf(str))).append("\u56FD\u9645\u7236\u4EB2\u8282").toString();
			break;
		}

		case 9: // '\t'
		{
			int tempDay = getFirstWeekDay(gregorianYear, gregorianMonth, 3) + 14;
			if (gregorianDay == tempDay)
				str = (new StringBuilder(String.valueOf(str))).append("\u56FD\u9645\u548C\u5E73\u65E5").toString();
			tempDay = getLastWeekDay(gregorianYear, gregorianMonth, 1);
			if (gregorianDay == tempDay)
				str = (new StringBuilder(String.valueOf(str))).append("\u56FD\u9645\u804B\u4EBA\u8282").toString();
			break;
		}

		case 10: // '\n'
		{
			int tempDay = getFirstWeekDay(gregorianYear, gregorianMonth, 2);
			if (gregorianDay == tempDay)
				str = (new StringBuilder(String.valueOf(str))).append("\u56FD\u9645\u4F4F\u623F\u65E5").toString();
			tempDay = getFirstWeekDay(gregorianYear, gregorianMonth, 4) + 7;
			if (gregorianDay == tempDay)
				str = (new StringBuilder(String.valueOf(str)))
						.append("\u56FD\u9645\u51CF\u8F7B\u81EA\u7136\u707E\u5BB3\u65E5").toString();
			break;
		}

		case 11: // '\013'
		{
			int tempDay = getFirstWeekDay(gregorianYear, gregorianMonth, 5) + 21;
			if (gregorianDay == tempDay)
				str = (new StringBuilder(String.valueOf(str))).append("\u611F\u6069\u8282").toString();
			break;
		}
		}
		return str;
	}

	public int getChineseDay() {
		return chineseDay;
	}

	public void setChineseDay(int chineseDay) {
		this.chineseDay = chineseDay;
	}

	public int getChineseMonth() {
		return chineseMonth;
	}

	public void setChineseMonth(int chineseMonth) {
		this.chineseMonth = chineseMonth;
	}

	public int getChineseYear() {
		return chineseYear;
	}

	public void setChineseYear(int chineseYear) {
		this.chineseYear = chineseYear;
	}

	public int getGregorianDay() {
		return gregorianDay;
	}

	public void setGregorianDay(int gregorianDay) {
		this.gregorianDay = gregorianDay;
	}

	public int getGregorianDayOfWeek() {
		return gregorianDayOfWeek;
	}

	public void setGregorianDayOfWeek(int gregorianDayOfWeek) {
		this.gregorianDayOfWeek = gregorianDayOfWeek;
	}

	public int getGregorianDayOfWeekInMonth() {
		return gregorianDayOfWeekInMonth;
	}

	public void setGregorianDayOfWeekInMonth(int gregorianDayOfWeekInMonth) {
		this.gregorianDayOfWeekInMonth = gregorianDayOfWeekInMonth;
	}

	public int getGregorianDayOfYear() {
		return gregorianDayOfYear;
	}

	public void setGregorianDayOfYear(int gregorianDayOfYear) {
		this.gregorianDayOfYear = gregorianDayOfYear;
	}

	public int getGregorianMonth() {
		return gregorianMonth;
	}

	public void setGregorianMonth(int gregorianMonth) {
		this.gregorianMonth = gregorianMonth;
	}

	public int getGregorianYear() {
		return gregorianYear;
	}

	public void setGregorianYear(int gregorianYear) {
		this.gregorianYear = gregorianYear;
	}

	public String display() {

		return new StringBuilder(getChineseYearStr()).append(getChineseAnimalStr()).append("年  ")
				.append(getChineseMonthStr()).append(getChineseDayStr()).append(" ").append(getTermStr()).append(" ")
				.append(getLunarFestaStr()).append(" ").append(getSolarFestaStr()).append(" ")
				.append(getOtherFestaStr()).append(" 星期").append(weekNames[getGregorianDayOfWeek()]).toString();

	}

	public String displayWeek() {

		return new StringBuilder().append(" 星期").append(weekNames[getGregorianDayOfWeek()]).toString();

	}

	private int gregorianYear;
	private int gregorianMonth;
	private int gregorianDay;
	private int chineseYear;
	private int chineseMonth;
	private int chineseDay;
	private int gregorianDayOfYear;
	private int gregorianDayOfWeek;
	private int gregorianDayOfWeekInMonth;
	private int sectionalTerm;
	private int principleTerm;

	private final String monthNames[] = { "\u4E00", "\u4E8C", "\u4E09", "\u56DB", "\u4E94", "\u516D", "\u4E03",
			"\u516B", "\u4E5D", "\u5341", "\u5341\u4E00", "\u5341\u4E8C" };

	private final static String weekNames[] = { "天", "一", "二", "三", "四", "五", "六" };

	private static int bigLeapMonthYears[] = { 6, 14, 19, 25, 33, 36, 38, 41, 44, 52, 55, 79, 117, 136, 147, 150, 155,
			158, 185, 193 };

	private final char chineseMonths[] = { '\0', '\004', '\255', '\b', 'Z', '\001', '\325', 'T', '\264', '\t', 'd',
			'\005', 'Y', 'E', '\225', '\n', '\246', '\004', 'U', '$', '\255', '\b', 'Z', 'b', '\332', '\004', '\264',
			'\005', '\264', 'U', 'R', '\r', '\224', '\n', 'J', '*', 'V', '\002', 'm', 'q', 'm', '\001', '\332', '\002',
			'\322', 'R', '\251', '\005', 'I', '\r', '*', 'E', '+', '\t', 'V', '\001', '\265', ' ', 'm', '\001', 'Y',
			'i', '\324', '\n', '\250', '\005', '\251', 'V', '\245', '\004', '+', '\t', '\236', '8', '\266', '\b',
			'\354', 't', 'l', '\005', '\324', '\n', '\344', 'j', 'R', '\005', '\225', '\n', 'Z', 'B', '[', '\004',
			'\266', '\004', '\264', '"', 'j', '\005', 'R', 'u', '\311', '\n', 'R', '\005', '5', 'U', 'M', '\n', 'Z',
			'\002', ']', '1', '\265', '\002', 'j', '\212', 'h', '\005', '\251', '\n', '\212', 'j', '*', '\005', '-',
			'\t', '\252', 'H', 'Z', '\001', '\265', '\t', '\260', '9', 'd', '\005', '%', 'u', '\225', '\n', '\226',
			'\004', 'M', 'T', '\255', '\004', '\332', '\004', '\324', 'D', '\264', '\005', 'T', '\205', 'R', '\r',
			'\222', '\n', 'V', 'j', 'V', '\002', 'm', '\002', 'j', 'A', '\332', '\002', '\262', '\241', '\251', '\005',
			'I', '\r', '\n', 'm', '*', '\t', 'V', '\001', '\255', 'P', 'm', '\001', '\331', '\002', '\321', ':', '\250',
			'\005', ')', '\205', '\245', '\f', '*', '\t', '\226', 'T', '\266', '\b', 'l', '\t', 'd', 'E', '\324', '\n',
			'\244', '\005', 'Q', '%', '\225', '\n', '*', 'r', '[', '\004', '\266', '\004', '\254', 'R', 'j', '\005',
			'\322', '\n', '\242', 'J', 'J', '\005', 'U', '\224', '-', '\n', 'Z', '\002', 'u', 'a', '\265', '\002', 'j',
			'\003', 'a', 'E', '\251', '\n', 'J', '\005', '%', '%', '-', '\t', '\232', 'h', '\332', '\b', '\264', '\t',
			'\250', 'Y', 'T', '\003', '\245', '\n', '\221', ':', '\226', '\004', '\255', '\260', '\255', '\004', '\332',
			'\004', '\364', 'b', '\264', '\005', 'T', '\013', 'D', ']', 'R', '\n', '\225', '\004', 'U', '"', 'm',
			'\002', 'Z', 'q', '\332', '\002', '\252', '\005', '\262', 'U', 'I', '\013', 'J', '\n', '-', '9', '6',
			'\001', 'm', '\200', 'm', '\001', '\331', '\002', '\351', 'j', '\250', '\005', ')', '\013', '\232', 'L',
			'\252', '\b', '\266', '\b', '\264', '8', 'l', '\t', 'T', 'u', '\324', '\n', '\244', '\005', 'E', 'U',
			'\225', '\n', '\232', '\004', 'U', 'D', '\265', '\004', 'j', '\202', 'j', '\005', '\322', '\n', '\222', 'j',
			'J', '\005', 'U', '\n', '*', 'J', 'Z', '\002', '\265', '\002', '\262', '1', 'i', '\003', '1', 's', '\251',
			'\n', 'J', '\005', '-', 'U', '-', '\t', 'Z', '\001', '\325', 'H', '\264', '\t', 'h', '\211', 'T', '\013',
			'\244', '\n', '\245', 'j', '\225', '\004', '\255', '\b', 'j', 'D', '\332', '\004', 't', '\005', '\260', '%',
			'T', '\003' };

	private final char sectionalTermMap[][] = {
			{ '\007', '\006', '\006', '\006', '\006', '\006', '\006', '\006', '\006', '\005', '\006', '\006', '\006',
					'\005', '\005', '\006', '\006', '\005', '\005', '\005', '\005', '\005', '\005', '\005', '\005',
					'\004', '\005', '\005' },
			{ '\005', '\004', '\005', '\005', '\005', '\004', '\004', '\005', '\005', '\004', '\004', '\004', '\004',
					'\004', '\004', '\004', '\004', '\003', '\004', '\004', '\004', '\003', '\003', '\004', '\004',
					'\003', '\003', '\003' },
			{ '\006', '\006', '\006', '\007', '\006', '\006', '\006', '\006', '\005', '\006', '\006', '\006', '\005',
					'\005', '\006', '\006', '\005', '\005', '\005', '\006', '\005', '\005', '\005', '\005', '\004',
					'\005', '\005', '\005', '\005' },
			{ '\005', '\005', '\006', '\006', '\005', '\005', '\005', '\006', '\005', '\005', '\005', '\005', '\004',
					'\005', '\005', '\005', '\004', '\004', '\005', '\005', '\004', '\004', '\004', '\005', '\004',
					'\004', '\004', '\004', '\005' },
			{ '\006', '\006', '\006', '\007', '\006', '\006', '\006', '\006', '\005', '\006', '\006', '\006', '\005',
					'\005', '\006', '\006', '\005', '\005', '\005', '\006', '\005', '\005', '\005', '\005', '\004',
					'\005', '\005', '\005', '\005' },
			{ '\006', '\006', '\007', '\007', '\006', '\006', '\006', '\007', '\006', '\006', '\006', '\006', '\005',
					'\006', '\006', '\006', '\005', '\005', '\006', '\006', '\005', '\005', '\005', '\006', '\005',
					'\005', '\005', '\005', '\004', '\005', '\005', '\005', '\005' },
			{ '\007', '\b', '\b', '\b', '\007', '\007', '\b', '\b', '\007', '\007', '\007', '\b', '\007', '\007',
					'\007', '\007', '\006', '\007', '\007', '\007', '\006', '\006', '\007', '\007', '\006', '\006',
					'\006', '\007', '\007' },
			{ '\b', '\b', '\b', '\t', '\b', '\b', '\b', '\b', '\007', '\b', '\b', '\b', '\007', '\007', '\b', '\b',
					'\007', '\007', '\007', '\b', '\007', '\007', '\007', '\007', '\006', '\007', '\007', '\007',
					'\006', '\006', '\007', '\007', '\007' },
			{ '\b', '\b', '\b', '\t', '\b', '\b', '\b', '\b', '\007', '\b', '\b', '\b', '\007', '\007', '\b', '\b',
					'\007', '\007', '\007', '\b', '\007', '\007', '\007', '\007', '\006', '\007', '\007', '\007',
					'\007' },
			{ '\t', '\t', '\t', '\t', '\b', '\t', '\t', '\t', '\b', '\b', '\t', '\t', '\b', '\b', '\b', '\t', '\b',
					'\b', '\b', '\b', '\007', '\b', '\b', '\b', '\007', '\007', '\b', '\b', '\b' },
			{ '\b', '\b', '\b', '\b', '\007', '\b', '\b', '\b', '\007', '\007', '\b', '\b', '\007', '\007', '\007',
					'\b', '\007', '\007', '\007', '\007', '\006', '\007', '\007', '\007', '\006', '\006', '\007',
					'\007', '\007' },
			{ '\007', '\b', '\b', '\b', '\007', '\007', '\b', '\b', '\007', '\007', '\007', '\b', '\007', '\007',
					'\007', '\007', '\006', '\007', '\007', '\007', '\006', '\006', '\007', '\007', '\006', '\006',
					'\006', '\007', '\007' } };

	private final char sectionalTermYear[][] = { { '\r', '1', 'U', 'u', '\225', '\271', '\311', '\372', '\372' },
			{ '\r', '-', 'Q', 'u', '\225', '\271', '\311', '\372', '\372' },
			{ '\r', '0', 'T', 'p', '\224', '\270', '\310', '\311', '\372' },
			{ '\r', '-', 'L', 'l', '\214', '\254', '\310', '\311', '\372' },
			{ '\r', ',', 'H', 'h', '\204', '\250', '\310', '\311', '\372' },
			{ '\005', '!', 'D', '`', '|', '\230', '\274', '\310', '\311' },
			{ '\035', '9', 'U', 'x', '\224', '\260', '\310', '\311', '\372' },
			{ '\r', '0', 'L', 'h', '\204', '\250', '\304', '\310', '\311' },
			{ '\031', '<', 'X', 'x', '\224', '\270', '\310', '\311', '\372' },
			{ '\020', ',', 'L', 'l', '\220', '\254', '\310', '\311', '\372' },
			{ '\034', '<', '\\', '|', '\240', '\300', '\310', '\311', '\372' },
			{ '\021', '5', 'U', '|', '\234', '\274', '\310', '\311', '\372' } };

	private final char principleTermMap[][] = {
			{ '\025', '\025', '\025', '\025', '\025', '\024', '\025', '\025', '\025', '\024', '\024', '\025', '\025',
					'\024', '\024', '\024', '\024', '\024', '\024', '\024', '\024', '\023', '\024', '\024', '\024',
					'\023', '\023', '\024' },
			{ '\024', '\023', '\023', '\024', '\024', '\023', '\023', '\023', '\023', '\023', '\023', '\023', '\023',
					'\022', '\023', '\023', '\023', '\022', '\022', '\023', '\023', '\022', '\022', '\022', '\022',
					'\022', '\022', '\022' },
			{ '\025', '\025', '\025', '\026', '\025', '\025', '\025', '\025', '\024', '\025', '\025', '\025', '\024',
					'\024', '\025', '\025', '\024', '\024', '\024', '\025', '\024', '\024', '\024', '\024', '\023',
					'\024', '\024', '\024', '\024' },
			{ '\024', '\025', '\025', '\025', '\024', '\024', '\025', '\025', '\024', '\024', '\024', '\025', '\024',
					'\024', '\024', '\024', '\023', '\024', '\024', '\024', '\023', '\023', '\024', '\024', '\023',
					'\023', '\023', '\024', '\024' },
			{ '\025', '\026', '\026', '\026', '\025', '\025', '\026', '\026', '\025', '\025', '\025', '\026', '\025',
					'\025', '\025', '\025', '\024', '\025', '\025', '\025', '\024', '\024', '\025', '\025', '\024',
					'\024', '\024', '\025', '\025' },
			{ '\026', '\026', '\026', '\026', '\025', '\026', '\026', '\026', '\025', '\025', '\026', '\026', '\025',
					'\025', '\025', '\026', '\025', '\025', '\025', '\025', '\024', '\025', '\025', '\025', '\024',
					'\024', '\025', '\025', '\025' },
			{ '\027', '\027', '\030', '\030', '\027', '\027', '\027', '\030', '\027', '\027', '\027', '\027', '\026',
					'\027', '\027', '\027', '\026', '\026', '\027', '\027', '\026', '\026', '\026', '\027', '\026',
					'\026', '\026', '\026', '\027' },
			{ '\027', '\030', '\030', '\030', '\027', '\027', '\030', '\030', '\027', '\027', '\027', '\030', '\027',
					'\027', '\027', '\027', '\026', '\027', '\027', '\027', '\026', '\026', '\027', '\027', '\026',
					'\026', '\026', '\027', '\027' },
			{ '\027', '\030', '\030', '\030', '\027', '\027', '\030', '\030', '\027', '\027', '\027', '\030', '\027',
					'\027', '\027', '\027', '\026', '\027', '\027', '\027', '\026', '\026', '\027', '\027', '\026',
					'\026', '\026', '\027', '\027' },
			{ '\030', '\030', '\030', '\030', '\027', '\030', '\030', '\030', '\027', '\027', '\030', '\030', '\027',
					'\027', '\027', '\030', '\027', '\027', '\027', '\027', '\026', '\027', '\027', '\027', '\026',
					'\026', '\027', '\027', '\027' },
			{ '\027', '\027', '\027', '\027', '\026', '\027', '\027', '\027', '\026', '\026', '\027', '\027', '\026',
					'\026', '\026', '\027', '\026', '\026', '\026', '\026', '\025', '\026', '\026', '\026', '\025',
					'\025', '\026', '\026', '\026' },
			{ '\026', '\026', '\027', '\027', '\026', '\026', '\026', '\027', '\026', '\026', '\026', '\026', '\025',
					'\026', '\026', '\026', '\025', '\025', '\026', '\026', '\025', '\025', '\025', '\026', '\025',
					'\025', '\025', '\025', '\026' } };

	private final char principleTermYear[][] = { { '\r', '-', 'Q', 'q', '\225', '\271', '\311' },
			{ '\025', '9', ']', '}', '\241', '\301', '\311' },
			{ '\025', '8', 'X', 'x', '\230', '\274', '\310', '\311' },
			{ '\025', '1', 'Q', 't', '\220', '\260', '\310', '\311' },
			{ '\021', '1', 'M', 'p', '\214', '\250', '\310', '\311' },
			{ '\034', '<', 'X', 't', '\224', '\264', '\310', '\311' },
			{ '\031', '5', 'T', 'p', '\220', '\254', '\310', '\311' },
			{ '\035', '9', 'Y', 'x', '\224', '\264', '\310', '\311' },
			{ '\021', '-', 'I', 'l', '\214', '\250', '\310', '\311' },
			{ '\034', '<', '\\', '|', '\240', '\300', '\310', '\311' },
			{ '\020', ',', 'P', 'p', '\224', '\264', '\310', '\311' },
			{ '\021', '5', 'X', 'x', '\234', '\274', '\310', '\311' } };

	private final char daysInGregorianMonth[] = { '\037', '\034', '\037', '\036', '\037', '\036', '\037', '\037',
			'\036', '\037', '\036', '\037' };

	private final String stemNames[] = { "\u7532", "\u4E59", "\u4E19", "\u4E01", "\u620A", "\u5DF1", "\u5E9A", "\u8F9B",
			"\u58EC", "\u7678" };

	private final String branchNames[] = { "\u5B50", "\u4E11", "\u5BC5", "\u536F", "\u8FB0", "\u5DF3", "\u5348",
			"\u672A", "\u7533", "\u9149", "\u620C", "\u4EA5" };

	private final String animalNames[] = { "\u9F20", "\u725B", "\u864E", "\u5154", "\u9F99", "\u86C7", "\u9A6C",
			"\u7F8A", "\u7334", "\u9E21", "\u72D7", "\u732A" };

	private final String chineseMonthNames[] = { "\u6B63", "\u4E8C", "\u4E09", "\u56DB", "\u4E94", "\u516D", "\u4E03",
			"\u516B", "\u4E5D", "\u5341", "\u51AC", "\u814A" };

	private final String principleTermNames[] = { "\u5927\u5BD2", "\u96E8\u6C34", "\u6625\u5206", "\u8C37\u96E8",
			"\u590F\u6EE1", "\u590F\u81F3", "\u5927\u6691", "\u5904\u6691", "\u79CB\u5206", "\u971C\u964D",
			"\u5C0F\u96EA", "\u51AC\u81F3" };

	private final String sectionalTermNames[] = { "\u5C0F\u5BD2", "\u7ACB\u6625", "\u60CA\u86F0", "\u6E05\u660E",
			"\u7ACB\u590F", "\u8292\u79CD", "\u5C0F\u6691", "\u7ACB\u79CB", "\u767D\u9732", "\u5BD2\u9732",
			"\u7ACB\u51AC", "\u5927\u96EA" };

	private final HashMap<String, String> solarFestaMap;

	private final HashMap<String, String> lunarFestaMap;

	public static void main(String args[]) throws Exception {

		ChineseCalendar c = new ChineseCalendar(2010, 2, 22);
		System.out.println((new StringBuilder("Gregorian Year:")).append(c.getGregorianYear()).toString());
		System.out.println((new StringBuilder("Gregorian Month:")).append(c.getGregorianMonth()).toString());
		System.out.println((new StringBuilder("Gregorian Day:")).append(c.getGregorianDay()).toString());
		System.out.println((new StringBuilder("Chinese Year:")).append(c.getChineseYear()).toString());
		System.out.println((new StringBuilder("Chinese Month:")).append(c.getChineseMonth()).toString());
		System.out.println((new StringBuilder("Chinese Day:")).append(c.getChineseDay()).toString());
		System.out.println((new StringBuilder("Gregorian DayOfYear:")).append(c.getGregorianDayOfYear()).toString());
		System.out.println(
				(new StringBuilder("Chinese DayOfWeek:")).append(weekNames[c.getGregorianDayOfWeek()]).toString());
		System.out.println(
				(new StringBuilder("Chinese DayOfWeekInMonth:")).append(c.getGregorianDayOfWeekInMonth()).toString());
		System.out.println(c.display());

	}
}
