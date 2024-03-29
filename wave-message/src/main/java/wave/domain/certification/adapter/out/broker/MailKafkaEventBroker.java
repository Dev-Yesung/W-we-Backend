package wave.domain.certification.adapter.out.broker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.mail.domain.vo.MailResult;
import wave.domain.mail.domain.port.MailEventBroker;

@RequiredArgsConstructor
@Component
public class MailKafkaEventBroker implements MailEventBroker {

	private static final String MAIL_SEND_RESULT = "mail_send_result";
	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	@Override
	public void publishMailSendResult(MailResult message) {
		kafkaProducerTemplate.send(MAIL_SEND_RESULT, message);
	}
}
