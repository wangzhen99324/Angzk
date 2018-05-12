package com.wz;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.wz.common.cache.impl.RedisCacheImpl;
//http://localhost:8080/Angzk-war/swagger-ui.html
//http://localhost:8080/Angzk-war/
//http://localhost:8080/Angzk-war/v2/api-docs
@SpringBootApplication
@EnableScheduling
@EnableSwagger2 //启动swagger注解 启动服务，浏览器输入"http://服务名:8080/swagger-ui.html"
@MapperScan("com.wz.mapper")
public class AngzkApplication extends SpringBootServletInitializer  {
	
	@Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(AngzkApplication.class);
    }


	@Autowired
	private StringRedisTemplate strRedis;

	public static void main(String[] args) {
		SpringApplication.run(AngzkApplication.class, args);
	}

	@Bean(name = "ThreadPoolTaskExecutor")
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(10);
		threadPoolTaskExecutor.setQueueCapacity(1000);
		threadPoolTaskExecutor.setKeepAliveSeconds(300);
		return threadPoolTaskExecutor;
	}

	@Bean(name = "redisCache")
	@Scope(value = "singleton")
	@Lazy()
	public RedisCacheImpl redisCacheImpl() {
		RedisCacheImpl redisCache = new RedisCacheImpl();
		redisCache.setRedisTemplate(strRedis);
		return redisCache;
	}
	
	 /**
	   * 生成API文档的入口
	   */
	  @Bean
	  public Docket generateApi() {
	    Docket docket = null;
	    // 可以根据配置决定不做任何API生成
//	    if (!appConfig.getAllowGenerateApi()) {
//	      docket = new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.none())
//	          .build();
//	    }

	    docket = new Docket(DocumentationType.SWAGGER_2)
	        .apiInfo(apiInfo())
	        .select()
	        // 标示只有被 @Api 标注的才能生成API.
	        .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
	        .paths(PathSelectors.any()).build()
	        .pathMapping("/")
	        .directModelSubstitute(LocalDate.class, String.class)
	        // 遇到 LocalDate时，输出成String
	        .genericModelSubstitutes(ResponseEntity.class)
	        .alternateTypeRules(
	            newRule(
	                typeResolver.resolve(DeferredResult.class,
	                    typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
	                typeResolver.resolve(WildcardType.class)))
	        .useDefaultResponseMessages(false);
	    return docket;
	  }

	private ApiInfo apiInfo() {
	    return new ApiInfo("智慧ETC系统接口", "-------Angzk", "1.0.0", "", "", "", "");
	  }

	  @Autowired
	  private TypeResolver typeResolver;




}
