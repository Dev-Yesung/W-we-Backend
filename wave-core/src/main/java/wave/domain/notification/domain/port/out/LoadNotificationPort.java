package wave.domain.notification.domain.port.out;

import java.util.List;

import wave.domain.account.domain.entity.User;
import wave.domain.notification.domain.entity.Notification;
import wave.domain.notification.dto.NotificationReadMessage;

public interface LoadNotificationPort {

	List<Notification> findAllUnreadNotification(User user);

}
