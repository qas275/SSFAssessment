package vttp2022.SsfAssessment.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import vttp2022.SsfAssessment.model.AllNews;
import vttp2022.SsfAssessment.model.Article;

@Configuration
public class RedisConfig {

    // @Value("${spring.redis.host")
    private String redisHost = "redis-17491.c252.ap-southeast-1-1.ec2.cloud.redislabs.com";

    // @Value("${spring.redis.port}")
    private Integer redisPort = 17491;

    // @Value("${spring.redis.password}")
    private String redisPassword = "weishunlim";

    // @Value("{spring.redis.database}")
    private String redisDb ="0";

    @Bean(name = "news")
    @Scope("singleton")
    public RedisTemplate<String, Article> redisTemplate() {
        Logger logger = LoggerFactory.getLogger(RedisTemplate.class);
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setPassword(redisPassword);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Article.class); //TODO set class

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        logger.info("redis host port > {redisHost} {redisPort}"+ redisHost+ redisPort);
        RedisTemplate<String, Article> template = new RedisTemplate<String, Article>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(template.getKeySerializer());
        template.setHashValueSerializer(template.getValueSerializer());
        return template;
    }


    
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
}