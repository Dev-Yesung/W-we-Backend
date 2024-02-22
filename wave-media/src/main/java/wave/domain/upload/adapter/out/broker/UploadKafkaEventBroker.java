package wave.domain.upload.adapter.out.broker;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import wave.domain.media.domain.port.out.broker.UploadEventBroker;
import wave.domain.media.dto.MediaFileUploadStatusMessage;
import wave.domain.media.dto.MediaUrlUpdateMessage;

@Component
public class UploadKafkaEventBroker implements UploadEventBroker {

	private final KafkaTemplate<String, Object> kafkaProducerTemplate;

	public UploadKafkaEventBroker(
		@Qualifier("kafkaTemplate") KafkaTemplate<String, Object> kafkaProducerTemplate
	) {
		this.kafkaProducerTemplate = kafkaProducerTemplate;
	}

	@Override
	public void publishUrlUpdateEvent(MediaUrlUpdateMessage message) {
		kafkaProducerTemplate.send("media_url_update", message);
	}

	@Override
	public void publishUploadStatusEvent(MediaFileUploadStatusMessage message) {
		kafkaProducerTemplate.send("media_upload_status_result", message);
	}

}
