package com.blog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
/**
 * 
 * @author dongwn mybatis-plus配置类
 *
 */
@Configuration
public class MyBatisPlusConfig {

	// mybatis-plus分页插件
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

}
