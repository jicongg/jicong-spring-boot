package cn.com.jicongg.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.com.jicongg.config.security.RSAUtil;

/**
 * spring 容器加载完成后执行lamezhi应用级别初始化操作. 2017年9月28日
 * 
 * @author jicong.
 */
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger logger = LoggerFactory.getLogger(MyApplicationListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			RSAUtil.initKey();
			logger.info("RSA init key is success!");
		} catch (Exception e) {
			logger.info("RSA init key is fail!" + e.getMessage());
		}
	}

}
