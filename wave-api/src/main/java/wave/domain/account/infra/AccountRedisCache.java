package wave.domain.account.infra;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import wave.config.CacheConfig;
import wave.domain.account.domain.port.out.AccountCache;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;

@Repository
public class AccountRedisCache implements AccountCache {

	private final ValueOperations<String, Object> valueOperations;
	private final CacheConfig cacheConfig;

	public AccountRedisCache(CacheConfig cacheConfig, RedisTemplate<String, Object> redisTemplate) {
		this.cacheConfig = cacheConfig;
		this.valueOperations = redisTemplate.opsForValue();
	}

	@Override
	public int cacheCertificationCode(Certification certification) {
		CertificationType type = certification.getType();
		String email = certification.getEmail();
		String key = getTaggedKey(type, email);
		String randomCode = certification.getCertificationCode();

		int ttl = type.getTtl();
		TimeUnit timeUnit = cacheConfig.getTimeUnitInSeconds();
		valueOperations.set(key, randomCode, ttl, timeUnit);

		return ttl;
	}

	@Override
	public Optional<String> getCertificationCode(Certification certification) {
		CertificationType type = certification.getType();
		String email = certification.getEmail();
		String key = getTaggedKey(type, email);
		String value = (String)valueOperations.get(key);

		return Optional.ofNullable(value);
	}

	@Override
	public Optional<String> getCertificationCode(CertificationType certificationType, String email) {
		String key = getTaggedKey(certificationType, email);
		String value = (String)valueOperations.get(key);

		return Optional.ofNullable(value);
	}

	@Override
	public Optional<String> removeCertificationCode(Certification certification) {
		CertificationType type = certification.getType();
		String email = certification.getEmail();
		String key = getTaggedKey(type, email);
		String value = (String)valueOperations.getAndDelete(key);

		return Optional.ofNullable(value);
	}

	@Override
	public boolean existCertificationCode(CertificationType type, String email) {
		String key = getTaggedKey(type, email);
		String value = (String)valueOperations.get(key);

		return value != null;
	}

	private String getTaggedKey(CertificationType type, String email) {
		String keySeparator = cacheConfig.getKeySeparator();

		return type.getEventName() + keySeparator + email;
	}
}
