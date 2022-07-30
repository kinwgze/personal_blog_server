package zeee.blog.common;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wz297
 * 2022/07/30
 */
@Configuration
public class JedisConfiguration {
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("127.0.0.1");
        configuration.setPort(6379);
        return configuration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration configuration) {
        final JedisConnectionFactory factory = new JedisConnectionFactory(configuration);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        return template;
    }

//    public static void main(String[] args) {
//        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JedisConfiguration.class);
//        final RedisTemplate<String, Object> redisTemplate = ctx.getBean(RedisTemplate.class);
//        final ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
//        opsForValue.set("China", "Peking");
//        System.out.println(opsForValue.get("China"));
//    }
}

