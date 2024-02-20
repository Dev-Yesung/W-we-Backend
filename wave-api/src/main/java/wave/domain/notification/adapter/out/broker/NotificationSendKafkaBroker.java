package wave.domain.notification.adapter.out.broker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import wave.domain.notification.domain.port.out.broker.NotificationSendEventBroker;

@RequiredArgsConstructor
@Component
public class NotificationSendKafkaBroker implements NotificationSendEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	@Override
	public void publishMessage(String topic, Object message) {
		kafkaProducerTemplate.send(topic, message);
	}

}
