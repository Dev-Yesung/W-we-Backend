package wave.domain.auth.infra;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import wave.domain.mail.CertificationType;

@Repository
public class UserCertificationRedisRepository implements UserCertificationRepository {
	private static final String SEPARATOR = ":";
	private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
	private final ValueOperations<String, Object> valueOperations;

	public UserCertificationRedisRepository(RedisTemplate<String, Object> redisTemplate) {
		this.valueOperations = redisTemplate.opsForValue();
	}

	@Override
	public boolean existCertificationCodeByEmailAndType(CertificationType type, String email) {
		String taggedKey = getTaggedKey(type, email);

		return valueOperations.get(taggedKey) != null;
	}

	@Override
	public Optional<String> getAndDeleteCertificationCodeByEmailAndType(CertificationType type, String email) {
		String key = getTaggedKey(type, email);
		String value = (String)valueOperations.getAndDelete(key);

		return Optional.ofNullable(value);
	}

	@Override
	public void saveCertificationCodeByEmailAndType(CertificationType type, String email, String code) {
		String key = getTaggedKey(type, email);
		long timeLimit = type.getTimeLimit();
		valueOperations.set(key, code, timeLimit, DEFAULT_TIME_UNIT);
	}

	private String getTaggedKey(CertificationType type, String email) {
		return type.getTypeName() + SEPARATOR + email;
	}
}
