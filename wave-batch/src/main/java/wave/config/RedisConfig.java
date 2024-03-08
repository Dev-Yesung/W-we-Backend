package wave.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	@Qualifier("redisCacheTemplate")
	public RedisTemplate<String, String> redisCacheTemplate() {
		RedisTemplate<String, String> redisCacheTemplate = new RedisTemplate<>();
		redisCacheTemplate.setConnectionFactory(redisConnectionFactory());

		redisCacheTemplate.setKeySerializer(new StringRedisSerializer());
		redisCacheTemplate.setValueSerializer(new StringRedisSerializer());

		return redisCacheTemplate;
	}
}
