package wave.domain.notification.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.notification.domain.entity.Notification;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
}
