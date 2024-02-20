package wave.domain.notification.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.account.domain.entity.User;

public interface AccountJpaRepository extends JpaRepository<User, Long> {
}
