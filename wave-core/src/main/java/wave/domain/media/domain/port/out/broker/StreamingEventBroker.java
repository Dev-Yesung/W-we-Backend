package wave.domain.media.domain.port.out.broker;

public interface StreamingEventBroker {

	Object publishAndReplyStreamingEvent(String requestTopic, Object message, String replyTopic);

	void publishStreamingEvent(String topic, Object message);

}
