package wave.domain.media.domain.port.out.broker;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

public interface UploadEventBroker {

	CompletableFuture<SendResult<String, Object>> publishMessageToTopic(String topic, Object message);

}
