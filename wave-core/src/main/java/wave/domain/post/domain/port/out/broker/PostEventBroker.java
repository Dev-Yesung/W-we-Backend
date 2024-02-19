package wave.domain.post.domain.port.out.broker;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.support.SendResult;

public interface PostEventBroker {

	CompletableFuture<SendResult<String, Object>> publishMessage(String topic, Object message);

}
