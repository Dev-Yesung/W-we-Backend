package wave.domain.chart.adapter.out.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import wave.domain.chart.domain.port.out.persistence.TrendChartCacheRepository;

@Repository
public class TrendChartRedisCacheRepository implements TrendChartCacheRepository {

	private final ValueOperations<String, String> stringValueOperations;

	public TrendChartRedisCacheRepository(
		@Qualifier("redisCacheTemplate") RedisTemplate<String, String> redisTemplate
	) {
		this.stringValueOperations = redisTemplate.opsForValue();
	}

	@Override
	public void save(String key, String value) {
		stringValueOperations.set(key, value);
	}

	@Override
	public Optional<String> get(String key) {
		return Optional.ofNullable(stringValueOperations.get(key));
	}

}
