/**
 * 
 */
package cn.com.jicongg.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author cong.ji
 *
 *         2017年10月11日
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <T> T getBean(Class<T> tClass) {
		return context.getBean(tClass);
	}

	public static <T> T getBean(String name, Class<T> tClass) {
		return context.getBean(name, tClass);
	}
}
