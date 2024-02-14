package wave.domain.account.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.account.domain.entity.User;

public interface AccountJpaRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
