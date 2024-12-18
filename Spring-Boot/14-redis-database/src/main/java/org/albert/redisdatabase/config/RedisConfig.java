package org.albert.redisdatabase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisRepositories
public class RedisConfig
{
    @Bean
    public JedisConnectionFactory jedisConnectionFactory()
    {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(1);

        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .usePooling().poolConfig(jedisPoolConfig).build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate()
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        // Converts keys from Java strings to byte arrays and back.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // Converts hash keys within Redis hashes (map-like structures) to byte arrays.
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // Converts stored values from objects to byte arrays.
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // Converts values in Redis hashes (map-like structures) from objects to byte arrays.
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Enable support for Redis transactions
        redisTemplate.setEnableTransactionSupport(true);
        // Initialize the template after setting properties
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
