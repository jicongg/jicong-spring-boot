package cn.com.jicongg.web.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置.
 * 
 * 2017年9月26日
 * 
 * @author jicong.
 */
public abstract class InterceptorAdapter extends WebMvcConfigurerAdapter {
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(new ExceptionInterceptor()).addPathPatterns("/**"); 
	}
	
}
