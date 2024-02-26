package com.wave.batch.domain.rank.adapter.out.persistence;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import wave.domain.rank.domain.port.out.persistence.PopularChartCacheRepository;

@Repository
public class PopularChartRedisCacheRepository implements PopularChartCacheRepository {

	private final ValueOperations<String, String> stringValueOperations;

	public PopularChartRedisCacheRepository(RedisTemplate<String, String> redisTemplate) {
		this.stringValueOperations = redisTemplate.opsForValue();
	}

	@Override
	public void save(String key, String value) {
		stringValueOperations.set(key, value);
	}

}
