package wave.domain.notification.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.notification.domain.entity.Notification;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {

	@Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.isRead = :isRead")
	List<Notification> findAllByUserIdAndIsRead(@Param("userId") Long userId, @Param("isRead") boolean isRead);

	Optional<Notification> findByPostId(Long postId);

}
