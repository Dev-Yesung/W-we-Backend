package wave.domain.account.infra;

import java.util.Optional;

import org.springframework.kafka.core.KafkaTemplate;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.vo.AccountEventType;
import wave.domain.account.domain.AccountLoadPort;
import wave.domain.account.domain.AccountUpdatePort;
import wave.domain.account.domain.PersistenceAdapter;
import wave.domain.account.domain.vo.Certification;
import wave.domain.mail.CertificationType;
import wave.domain.account.domain.entity.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class AccountPersistenceAdapter
	implements AccountLoadPort, AccountUpdatePort {

	private final AccountJpaRepository accountRepository;
	private final AccountCache accountCache;
	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	@Override
	public Optional<User> findAccountByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public void checkDuplicateEmail(String email) {
		accountRepository.findByEmail(email)
			.ifPresent(user -> {
				throw new EntityException(ErrorCode.ALREADY_EXIST_USER_EMAIL);
			});
	}

	@Override
	public String getCertificationCode(Certification certification) {
		return accountCache.getCertificationCode(certification)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE));
	}

	@Override
	public boolean isExistCertificationCode(CertificationType certificationType, String email) {
		return accountCache.isExistCertificationCode(certificationType, email);
	}

	@Override
	public void publishCertificationEvent(Certification certification) {
		CertificationType certificationType = certification.getType();
		String topic = certificationType.getName();
		kafkaProducerTemplate.send(topic, certification);
	}

	@Override
	public User saveAccount(User user) {
		return accountRepository.save(user);
	}

	@Override
	public void publishNewAccountEvent(User user) {
		String topic = AccountEventType.SIGNUP.name();
		String email = user.getEmail();
		kafkaProducerTemplate.send(topic, email);
	}

	@Override
	public int cacheCertificationCode(Certification certification) {
		return accountCache.cacheCertificationCode(certification);
	}

	@Override
	public void removeCertificationCode(Certification certification) {
		accountCache.removeCertificationCode(certification)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_CERTIFICATION_CODE));
	}
}
