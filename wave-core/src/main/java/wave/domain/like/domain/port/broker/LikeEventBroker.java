package wave.domain.like.domain.port.broker;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

public interface LikeEventBroker {

	CompletableFuture<SendResult<String, Object>> publishMessage(String topic, Object message);

}
