package wave.domain.account.infra;

import org.springframework.kafka.core.KafkaTemplate;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.AccountEventType;
import wave.domain.account.domain.AccountLoadPort;
import wave.domain.account.domain.AccountUpdatePort;
import wave.domain.account.domain.Certification;
import wave.domain.account.domain.PersistenceAdapter;
import wave.domain.mail.CertificationType;
import wave.domain.user.domain.User;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class AccountPersistenceAdapter
	implements AccountLoadPort, AccountUpdatePort {

	private final AccountJpaRepository accountRepository;
	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	@Override
	public void checkDuplicateEmail(String email) {
		accountRepository.findByEmail(email)
			.ifPresent(user -> {
				throw new EntityException(ErrorCode.ALREADY_EXIST_USER_EMAIL);
			});
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
}
