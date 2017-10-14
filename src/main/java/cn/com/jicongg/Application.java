package cn.com.jicongg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * spring boot init类. @EnableTransactionManagement 启动注解事务控制. 
 * @ComponentScan 包扫描 cn.com.jicongg.
 *
 * @author jicong. 2017年9月20日
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.com.jicongg"})
public class Application extends SpringBootServletInitializer {

  /** spring boot 打包成war 继承 SpringBootServletInitializer. */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  /**
   * spring boot 启动类.
   *
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
