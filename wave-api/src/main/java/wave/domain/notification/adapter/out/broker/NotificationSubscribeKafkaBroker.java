package wave.domain.notification.adapter.out.broker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.notification.domain.port.out.broker.NotificationSendEventBroker;

@Component
public class NotificationSubscribeKafkaBroker implements NotificationSendEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	public NotificationSubscribeKafkaBroker(
		@Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaProducerTemplate
	) {
		this.kafkaProducerTemplate = kafkaProducerTemplate;
	}

	@Override
	public void publishMessage(String topic, Object message) {
		kafkaProducerTemplate.send(topic, message);
	}

}
