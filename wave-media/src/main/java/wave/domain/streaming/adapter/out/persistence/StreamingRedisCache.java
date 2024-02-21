package wave.domain.streaming.adapter.out.persistence;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import wave.domain.media.domain.port.out.persistence.StreamingCacheRepository;

@Repository
public class StreamingRedisCache implements StreamingCacheRepository {

	private final ValueOperations<String, String> valueOperations;

	public StreamingRedisCache(
		@Qualifier("redisCacheTemplate") RedisTemplate<String, String> redisTemplate
	) {
		this.valueOperations = redisTemplate.opsForValue();
	}

	@Override
	public void setValueAndTimeout(String key, String value) {
		valueOperations.set(key, value, 3L, TimeUnit.MINUTES);
	}

	@Override
	public Optional<String> getAndDelete(String key) {
		String value = String.valueOf(valueOperations.getAndDelete(key));

		return Optional.ofNullable(value);
	}

}
