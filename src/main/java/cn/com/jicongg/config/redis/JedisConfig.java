package cn.com.jicongg.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author cong.ji
 *     <p>Date: 2017-10-13
 */
@Configuration
public class JedisConfig {

  @Value("${jedis.pool.host}")
  private String host;

  @Value("${jedis.pool.port}")
  private int port;

  @Value("${jedis.pool.config.maxTotal}")
  private int maxTotal;

  @Value("${jedis.pool.config.maxIdle}")
  private int maxIdle;

  @Value("${jedis.pool.config.maxWaitMillis}")
  private int maxWaitMillis;

  @Bean(name = "jedis.pool")
  @Autowired
  public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config) {
    return new JedisPool(config, host, port);
  }

  @Bean(name = "jedis.pool.config")
  public JedisPoolConfig jedisPoolConfig() {
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(maxTotal);
    config.setMaxIdle(maxIdle);
    config.setMaxWaitMillis(maxWaitMillis);
    return config;
  }
}
