package wave.domain.notification.port.out;

import java.util.List;

import wave.domain.account.domain.entity.User;
import wave.domain.notification.entity.Notification;

public interface LoadNotificationPort {

	List<Notification> findAllUnreadNotification(User user);

}
