package com.example.carparkapp.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.carparkapp.Models.HDBCarParkInfo;

@Configuration
public class AppConfig {

  // Inject the properties from application.properties into the configuration.
  @Value("${spring.redis.host}")
  private String redisHost; // Railway: REDIS_HOST

  @Value("${spring.redis.port}")
  private Integer redisPort; // Railway: REDIS_PORT

  @Value("${spring.redis.username}")
  private String redisUser; // Railway: REDIS_USERNAME

  @Value("${spring.redis.password}")
  private String redisPassword; // Railway: REDIS_PASSWORD

  @Value("${spring.redis.database}")
  private Integer redisDatabase;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {

    // Sets your redis host and port (from application.properties).
    RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration(redisHost, redisPort);

    // Sets your redis username and password.
    if (redisUser != null && !redisUser.isEmpty()) {
      rsc.setUsername(redisUser);
    }

    if (redisPassword != null && !redisPassword.isEmpty()) {
      rsc.setPassword(redisPassword);
    }

    // Builds the client.
    JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
    // Because the Jedis Conneciton Factory requires the client.
    JedisConnectionFactory jedisFac = new JedisConnectionFactory(rsc, jedisClient);
    jedisFac.afterPropertiesSet(); // Performs validation of the configuration.

    return jedisFac;
  }

  @Bean
  public RedisTemplate<String, Object> redisObjectTemplate() {
    RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());

    return redisTemplate;
  }

  @Bean("hdbcache")
  public RedisTemplate<String, HDBCarParkInfo> redisHDBCarParkTemplate() {
    RedisTemplate<String,HDBCarParkInfo> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new HDBCarParkInfoSerializer()); // Custom Serializer.

    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
