package wave.domain.media.adapter.out.broker;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import wave.domain.media.domain.port.out.broker.ImageEventBroker;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EventException;

@Slf4j
@Component
public class ImageKafkaEventBroker implements ImageEventBroker {

	private final ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate;

	public ImageKafkaEventBroker(
		@Qualifier("replyingImageFileKafkaTemplate")
		ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate
	) {
		this.replyingKafkaTemplate = replyingKafkaTemplate;
	}

	@Override
	public Object publishAndReplyImageEvent(String requestTopic, Object message, String replyTopic) {
		ProducerRecord<String, Object> record = new ProducerRecord<>(requestTopic, message);
		record.headers().add(KafkaHeaders.REPLY_TOPIC, replyTopic.getBytes());

		var replyFuture = replyingKafkaTemplate.sendAndReceive(record);
		try {
			SendResult<String, Object> sendResult = replyFuture.getSendFuture().get();
			log.info("전송 정보 : " + sendResult.getRecordMetadata());
			ConsumerRecord<String, Object> consumerRecord = replyFuture.get();
			log.info("Return value: " + consumerRecord.value());

			return consumerRecord.value();
		} catch (InterruptedException | ExecutionException e) {
			throw new EventException(ErrorCode.UNABLE_TO_REQUEST_STREAMING, e);
		}
	}

}
