package cn.com.jicongg.config.mybatis;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;

import cn.com.jicongg.config.druid.DruidConfig;

/**
 *
 * 2017年9月20日
 * 
 * @author jicong.
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

	private final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
	
	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;

	@Autowired
	private DruidConfig.MyDataSource druidConfig = new DruidConfig().new MyDataSource();

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {

		try {
			SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
			bean.setDataSource(druidConfig.dataSource());
			bean.setTypeAliasesPackage("cn.com.jicongg.*.entity");

			// 分页插件设置
			PageHelper pageHelper = new PageHelper();
			Properties properties = new Properties();
			properties.setProperty("reasonable", "true");
			properties.setProperty("supportMethodsArguments", "true");
			properties.setProperty("returnPageInfo", "check");
			properties.setProperty("params", "count=countSql");
			pageHelper.setProperties(properties);

			// 添加分页插件
			bean.setPlugins(new Interceptor[] { pageHelper });

			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

			// 基于注解扫描Mapper，不需配置xml路径
			bean.setMapperLocations(resolver.getResources(mapperLocations));
			logger.info("init mybatis sql session factory bean!");
			return bean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(druidConfig.dataSource());
	}

}
