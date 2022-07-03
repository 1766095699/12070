package com.music.musicauth.config;

import com.music.musicauth.serialier.MyStringRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisConfig
 * @Description TODO
 * @Author 86183
 * @Date2021-09-2220:07
 * @Version 1.0
 **/
@Configuration
public class RedisConfig {
    //这个配置类是为了防止出现序列化的时候乱码的情况,所以要自己来指定序列化的方法
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new MyStringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

//    /**
//     * Lettuce 配置 Sentinel
//     */
//    @Bean
//    public RedisConnectionFactory lettuceConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                // 主节点名称
//                .master("mymaster")
//                // 主从服务器地址
//                .sentinel("115.159.220.44", 26379)
//                .sentinel("106.14.15.61", 26379)
//                .sentinel("115.159.220.44", 26379);
//        // 设置密码
//        sentinelConfig.setPassword("123456");
//        LettucePoolingClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
//                // 读写分离，若主节点能抗住读写并发，则不需要设置，全都走主节点即可
//                .readFrom(ReadFrom.ANY)
//                .build();
//        return new LettuceConnectionFactory(sentinelConfig,lettuceClientConfiguration);
//    }


//    /**
//     * Jedis 配置 Sentinel
//     */
//    @Bean
//    public RedisConnectionFactory jedisConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                // 主节点名称
//                .master("mymaster")
//                // 主从服务器地址
//                .sentinel("192.168.18.10", 26379)
//                .sentinel("192.168.18.10", 26379)
//                .sentinel("192.168.18.10", 26379);
//        // 设置密码
//        sentinelConfig.setPassword("123456");
//        return new JedisConnectionFactory(sentinelConfig);
//    }


}
