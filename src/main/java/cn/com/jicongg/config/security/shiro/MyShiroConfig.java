package cn.com.jicongg.config.security.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.com.jicongg.config.security.shiro.ShiroConfig;

@Configuration
public class MyShiroConfig extends ShiroConfig {

  private static final Logger log = LoggerFactory.getLogger(ShiroFilterFactoryBean.class);

  @Bean(name = "securityManager")
  public SecurityManager securityManager() {
    log.info("securityManager()");
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 设置realm.
    securityManager.setRealm(this.myAuthRealm());

    // 注入缓存管理器;
    securityManager.setCacheManager(super.ehCacheManager()); // 这个如果执行多次，也是同样的一个对象;

    // session管理器
    // securityManager.setSessionManager(super.configWebSessionManager());

    // 设置rememberMe管理器
    securityManager.setRememberMeManager(super.rememberMeManager());

    return securityManager;
  }

  /**
   * realm
   *
   * @return
   */
  @Bean(value = "authRealm")
  public MyAuthorizingRealm myAuthRealm() {
    log.info("myShiroRealm()");
    MyAuthorizingRealm mallAuthorizingRealm = new MyAuthorizingRealm();
    return mallAuthorizingRealm;
  }
}
