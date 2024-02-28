package wave.domain.media.domain.port.out.broker;

public interface ImageEventBroker {

	Object publishAndReplyImageEvent(String requestTopic, Object message, String replyTopic);

}
