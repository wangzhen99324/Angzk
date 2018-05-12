package com.wz.business.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wz.bean.bean.Result;
import com.wz.business.demo.model.User;
import com.wz.business.demo.service.BstuService;
import com.wz.common.cache.Cache;
import com.wz.common.cache.impl.RedisCacheImpl;
import com.wz.common.utils.DateUtils;
import com.wz.model.Bstu;

@Api(value = "测试业务类", produces = "application/json")
@RestController
@RequestMapping("/demo")
public class HelloController {
	
	@Autowired
	private BstuService bstuService;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	@Qualifier("ehCache")
	private Cache cache;
	
	@Autowired
	@Qualifier("redisCache")
	private RedisCacheImpl redisCache;
	
	@ApiOperation(value = "测试hello", httpMethod = "GET")
	@RequestMapping("hello")
	public Result hello(){
		System.err.println("hello");
		String s = "new Linux";
		return new Result(s);
	}
	
	@ApiOperation(value = "获取时间", httpMethod = "GET")
	@RequestMapping("/date")
	public Result date(){
		System.err.println("date");
		Date data = new Date();
		return new Result(data);
	}
	
	@ApiOperation(value = "获取用户列表", notes = "获取用户列表", consumes = "application/json",httpMethod = "GET")
	@RequestMapping("/getUser")
	public Result getUser(){
		System.err.println("getUser");
		List<Bstu> list = bstuService.findAllBstu();
		return new Result(list);
	}
	
	@ApiOperation(value = "获取用户", httpMethod = "GET")
	@RequestMapping("user")
	public Result Tser(){
		System.err.println("user");
		User user = new User();
		user.setId("1");
		user.setUserName("Ankgz");
		String data = DateUtils.getDateToString("yyyy-MM-dd HH:mm:ss a", System.currentTimeMillis());
		user.setPassword("12345678");
		user.setDate(data);
		return new Result(user);
	}
	
	@RequestMapping("geterror")
	@ApiOperation(value = "error测试", httpMethod = "GET")
	public Result geterro(){
		System.err.println("getUser");
		int a = 100 /0;
		return new Result(a);
	}
	
	@ApiOperation(value = "同步测试", httpMethod = "GET")	
	@RequestMapping("thread")
	public Result thread(){
		long millis = System.currentTimeMillis();
		bstuService.task1();
		bstuService.task2();
		bstuService.task3();
		long end = System.currentTimeMillis();
		
		end = end - millis;
		System.err.println("耗时：---》"+end);
		return new Result();
	}
	@ApiOperation(value = "异步线程池测试", httpMethod = "GET")
	@RequestMapping("taskThread")
	public Result taskThread(){
		long millis = System.currentTimeMillis();
		taskExecutor.submit(() -> {
			bstuService.task1();
			bstuService.task2();
			bstuService.task3();
			
		});
		long end = System.currentTimeMillis();
		
		end = end - millis;
		System.err.println("耗时：---》"+end);
		return new Result();
	}
	
	@ApiOperation(value = "异步线程池测试", httpMethod = "GET")
	@RequestMapping("taskThreadExecute")
	public Result taskThreadExecute(){
		long millis = System.currentTimeMillis();
		taskExecutor.execute(() -> {
			bstuService.task1();
			bstuService.task2();
			bstuService.task3();
			
		});
		long end = System.currentTimeMillis();
		
		end = end - millis;
		System.err.println("耗时：---》"+end);
		return new Result();
	}
	@ApiOperation(value = "ehcache测试", httpMethod = "GET")
	@RequestMapping("cacheUser")
	public Result cacheUser(){
		cache.set("user_001", "王震");
		String object = (String) cache.get("user_001");
		return new Result(object);
	}
	@ApiOperation(value = "ehcache List 测试", httpMethod = "GET")
	@RequestMapping("cacheList")
	public Result cacheList(){
		List<User> list  = new ArrayList<User>();
		User user = new User();
		user.setId("1");
		user.setUserName("Ankgz");
		String data = DateUtils.getDateToString("yyyy-MM-dd HH:mm:ss a", System.currentTimeMillis());
		user.setPassword("12345678");
		user.setDate(data);
		
		User user1 = new User();
		user1.setId("2");
		user1.setUserName("Ankgz2");
		String data2 = DateUtils.getDateToString("yyyy-MM-dd HH:mm:ss a", System.currentTimeMillis());
		user1.setPassword("12345678");
		user1.setDate(data2);
		list.add(user);
		list.add(user1);
		
		
		cache.set("user_List",list);
		List<User> listUser = cache.getList("user_List", User.class);
		return new Result(listUser);
	}
	@ApiOperation(value = "rediscache  测试", httpMethod = "GET")
	@RequestMapping("redisCacheUser")
	public Result redisCacheUser(){
		User user = new User();
		user.setId("1");
		user.setUserName("Ankgz");
		String data = DateUtils.getDateToString("yyyy-MM-dd HH:mm:ss a", System.currentTimeMillis());
		user.setPassword("12345678");
		user.setDate(data);
		redisCache.set("json:user:info:"+user.getId(), user);
		User user2 = redisCache.get("json:user:info:"+user.getId(),User.class);
		return new Result(user2);
	}
	@ApiOperation(value = "rediscache List 测试", httpMethod = "GET")
	@RequestMapping("redisCacheList")
	public Result redisCacheList(){
		List<User> list  = new ArrayList<User>();
		User user = new User();
		user.setId("1");
		user.setUserName("Ankgz");
		String data = DateUtils.getDateToString("yyyy-MM-dd HH:mm:ss a", System.currentTimeMillis());
		user.setPassword("12345678");
		user.setDate(data);
		
		User user1 = new User();
		user1.setId("2");
		user1.setUserName("Ankgz2");
		String data2 = DateUtils.getDateToString("yyyy-MM-dd HH:mm:ss a", System.currentTimeMillis());
		user1.setPassword("12345678");
		user1.setDate(data2);
		list.add(user);
		list.add(user1);
		
		redisCache.set("json_userList", list);
		 List<User> list2 = redisCache.getList("json_userList", User.class);
		return new Result(list2);
	}
	
	
	
}
