package wave.domain.notification.port.out;

import wave.domain.notification.dto.UnreadMessageSendEvent;

public interface LoadNotificationPort {

	void sendUnreadMessage(UnreadMessageSendEvent unreadMessageSendEvent);

}
