package cn.com.jicongg.config.mybatis;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.com.jicongg.mapper.BaseMapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;


/**
 * mybatis scan.
 * 2017年9月20日
 * 
 * @author jicong.
 */
@Configuration
// 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
	
	private final Logger logger = LoggerFactory.getLogger(MyBatisMapperScannerConfig.class);

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.lamezhi.**.mapper");

		// 初始化扫描器的相关配置，这里我们要创建一个Mapper的父类
		Properties properties = new Properties();
		properties.setProperty("mappers", BaseMapper.class.getName());
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");

		mapperScannerConfigurer.setProperties(properties);
		logger.info("init myBatis mapper scanner config!");
		return mapperScannerConfigurer;
	}

}
