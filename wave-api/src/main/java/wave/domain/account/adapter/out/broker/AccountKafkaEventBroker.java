package wave.domain.account.adapter.out.broker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.port.out.AccountEventBroker;
import wave.domain.account.domain.vo.AccountEventType;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;

@RequiredArgsConstructor
@Component
public class AccountKafkaEventBroker implements AccountEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	@Override
	public void publishCertificationEvent(Certification certification) {
		CertificationType certificationType = certification.getType();
		String topic = certificationType.getEventName();
		kafkaProducerTemplate.send(topic, certification);
	}

	@Override
	public void publishNewAccountEvent(User user) {
		String topic = AccountEventType.SIGNUP.name();
		String email = user.getEmail();
		kafkaProducerTemplate.send(topic, email);
	}
}
