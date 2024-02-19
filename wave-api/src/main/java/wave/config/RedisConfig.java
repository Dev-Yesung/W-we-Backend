package wave.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wave.domain.notification.dto.PostNotificationMessage;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {

	private final ObjectMapper objectMapper;

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}

	@Bean
	@Qualifier("redisTemplate")
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());

		return redisTemplate;
	}

	@Bean
	@Qualifier("notificationRedisTemplate")
	public RedisOperations<String, PostNotificationMessage> notificationRedisTemplate() {
		Jackson2JsonRedisSerializer<PostNotificationMessage> jsonRedisSerializer
			= new Jackson2JsonRedisSerializer<>(PostNotificationMessage.class);
		jsonRedisSerializer.setObjectMapper(objectMapper);

		RedisTemplate<String, PostNotificationMessage> notificationRedisTemplate = new RedisTemplate<>();
		notificationRedisTemplate.setConnectionFactory(redisConnectionFactory());

		notificationRedisTemplate.setKeySerializer(RedisSerializer.string());
		notificationRedisTemplate.setValueSerializer(jsonRedisSerializer);
		notificationRedisTemplate.setHashKeySerializer(RedisSerializer.string());
		notificationRedisTemplate.setHashValueSerializer(jsonRedisSerializer);

		return notificationRedisTemplate;
	}

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer() {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory());

		return redisMessageListenerContainer;
	}
}
