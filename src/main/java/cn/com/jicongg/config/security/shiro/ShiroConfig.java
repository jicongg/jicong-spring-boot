package cn.com.jicongg.config.security.shiro;

import java.util.LinkedHashMap;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.com.jicongg.config.redis.RedisSessionDao;

@Configuration
@ConfigurationProperties(prefix = "shiro")
public abstract class ShiroConfig {

  private static final Logger log = LoggerFactory.getLogger(ShiroFilterFactoryBean.class);

  private String configLocation;

  private LinkedHashMap<String, String> filterLinkedMap = new LinkedHashMap<String, String>();
  
  @Autowired
  private RedisSessionDao redisSessionDao;

  /* @Bean(name = "securityManager")
  public SecurityManager securityManager(
      @Qualifier("authRealm") LmzMallAuthorizingRealm authRealm,
      @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager) {
    log.info("securityManager()");
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 设置realm.
    securityManager.setRealm(authRealm);

    // 设置rememberMe管理器
    securityManager.setRememberMeManager(cookieRememberMeManager);

    return securityManager;
  }

  */
  /**
   * realm
   *
   * @return
   */
  /*
  @Bean(value = "authRealm")
  public LmzMallAuthorizingRealm myAuthRealm(
      @Qualifier("ehCacheManager") EhCacheManager ehCacheManager) {
    log.info("myShiroRealm()");
    LmzMallAuthorizingRealm myAuthorizingRealm = new LmzMallAuthorizingRealm();
    // 设置密码凭证匹配器
    // myAuthorizingRealm.setCredentialsMatcher(matcher); //
    // myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    // 设置缓存管理器
    myAuthorizingRealm.setCacheManager(ehCacheManager);

    return myAuthorizingRealm;
  }*/

  /**
   * session 管理器.
   *
   * @return
   */
  @Bean(value = "defaultWebSessionManager")
  public DefaultWebSessionManager configWebSessionManager() {
    DefaultWebSessionManager manager = new DefaultWebSessionManager();
    manager.setSessionDAO(redisSessionDao); // 设置SessionDao
    manager.setDeleteInvalidSessions(true); // 删除过期的session
//    manager.setGlobalSessionTimeout(redisSessionDao.getExpireTime()); // 设置全局session超时时间
//    manager.setSessionValidationSchedulerEnabled(true); // 是否定时检查session
    return manager;
  }
  /**
   * 缓存管理器
   *
   * @return
   */
  @Bean(value = "ehCacheManager")
  public EhCacheManager ehCacheManager() {
    log.info("ehCacheManager()");
    EhCacheManager cacheManager = new EhCacheManager();
    cacheManager.setCacheManagerConfigFile(configLocation);
    return cacheManager;
  }

  /**
   * 缓存管理器
   *
   * @return
   */
  @Bean(value = "redisCacheManager")
  public RedisCacheManager redisCacheManager() {
    log.info("redisManager()");
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    RedisManager redisManager = new RedisManager();
    //to do
    redisCacheManager.setRedisManager(redisManager);
    return redisCacheManager;
  }

  /**
   * cookie对象;
   *
   * @return
   */
  @Bean
  public SimpleCookie rememberMeCookie() {
    log.info("rememberMeCookie()");
    // 这个参数是cookie的名称，对应前端的checkbox 的name = rememberMe
    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    // <!-- 记住我cookie生效时间30天（259200） ,单位秒;-->
    simpleCookie.setMaxAge(259200);
    return simpleCookie;
  }

  /**
   * 记住我管理器 cookie管理对象;
   *
   * @return
   */
  @Bean(name = "cookieRememberMeManager")
  public CookieRememberMeManager rememberMeManager() {
    log.info("hashedCredentialsMatcher()");
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(rememberMeCookie());
    return cookieRememberMeManager;
  }

  /**
   * 密码匹配凭证管理器
   *
   * @return
   */
  // @Bean(name = "hashedCredentialsMatcher")
  // public HashedCredentialsMatcher hashedCredentialsMatcher() {
  // log.info("hashedCredentialsMatcher()");
  // HashedCredentialsMatcher hashedCredentialsMatcher = new
  // HashedCredentialsMatcher();
  // // 散列算法:这里使用MD5算法;
  // hashedCredentialsMatcher.setHashAlgorithmName("MD5");
  // // 散列的次数，比如散列两次，相当于md5(md5(""));
  // hashedCredentialsMatcher.setHashIterations(1024);
  // return hashedCredentialsMatcher;
  // }

  /**
   * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持; Controller才能使用@RequiresPermissions
   *
   * @param securityManager
   * @return
   */
  //	@Bean
  //	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
  //			@Qualifier("securityManager") SecurityManager securityManager) {
  //		log.info("authorizationAttributeSourceAdvisor()");
  //		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
  //		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
  //		return authorizationAttributeSourceAdvisor;
  //	}

  @Bean
  public ShiroFilterFactoryBean shiroFilter(
      @Qualifier("securityManager") SecurityManager securityManager) {
    log.info("shirFilter()");
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

    // 必须设置 SecurityManager
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    // 拦截器.
    //		Map<String, String> map = new LinkedHashMap<String, String>();
    //		map.put("/logout/**", "logout");
    //		map.put("/login/**", "anon");
    //		map.put("/logon/**", "anon");
    //
    //		map.put("/druid/**", "anon");
    //
    //		map.put("/user/**", "anon");
    //		map.put("/goods/**", "anon");
    //
    //		map.put("/**", "authc");

    // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
    shiroFilterFactoryBean.setLoginUrl("/login");
    // 登录成功后要跳转的链接
    shiroFilterFactoryBean.setSuccessUrl("/index");
    // 未授权界面;
    shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterLinkedMap);
    return shiroFilterFactoryBean;
  }

  public String getConfigLocation() {
    return configLocation;
  }

  public void setConfigLocation(String configLocation) {
    this.configLocation = configLocation;
  }

  public LinkedHashMap<String, String> getFilterLinkedMap() {
    return filterLinkedMap;
  }

  public void setFilterLinkedMap(LinkedHashMap<String, String> filterLinkedMap) {
    this.filterLinkedMap = filterLinkedMap;
  }
}
