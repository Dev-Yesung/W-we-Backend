package wave.domain.notification.adapter.out;

import java.util.List;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.notification.adapter.out.persistence.NotificationJpaRepository;
import wave.domain.notification.entity.Notification;
import wave.domain.notification.port.out.LoadNotificationPort;
import wave.global.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class NotificationPersistenceAdapter implements LoadNotificationPort {

	private final NotificationJpaRepository notificationJpaRepository;

	@Override
	public List<Notification> findAllUnreadNotification(User user) {
		Long userId = user.getId();
		return notificationJpaRepository.findAllByUserIdAndIsRead(userId, false);
	}

}
