package top.nodaoli.easychat.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.context.annotation.Bean;

/**
 * Redis配置类
 * 用于配置RedisTemplate实例，自定义序列化方式，优化Redis数据存储和读取
 */
@Configuration
public class RedisConfig<V> {

    /**
     * 创建并配置RedisTemplate实例
     * RedisTemplate是Spring Data Redis提供的核心操作类，用于与Redis进行交互
     *
     * @param factory Redis连接工厂，由Spring自动注入，提供Redis连接管理
     * @return 配置好的RedisTemplate实例，已设置字符串和JSON序列化器
     */
    @Bean
    public RedisTemplate<String, V> redisTemplate(RedisConnectionFactory factory) {
        // 创建RedisTemplate实例
        RedisTemplate<String, V> template = new RedisTemplate<>();
        // 设置Redis连接工厂
        template.setConnectionFactory(factory);
        // 配置键序列化器：使用StringRedisSerializer
        template.setKeySerializer(RedisSerializer.string());
        // 配置值序列化器：使用GenericJackson2JsonRedisSerializer
        template.setValueSerializer(RedisSerializer.json());
        // 配置哈希键序列化器：使用StringRedisSerializer
        template.setHashKeySerializer(RedisSerializer.string());
        // 配置哈希值序列化器：使用GenericJackson2JsonRedisSerializer
        template.setHashValueSerializer(RedisSerializer.json());
        // 初始化参数设置
        template.afterPropertiesSet();
        return template;
    }
}
