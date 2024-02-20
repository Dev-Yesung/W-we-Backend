package wave.domain.notification.domain.port.out.broker;

import wave.domain.account.domain.entity.User;

public interface NotificationSendEventBroker {

	void publishMessage(String topic, Object message);

}
