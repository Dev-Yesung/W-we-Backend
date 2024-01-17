package wave.domain.account.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.user.domain.User;

public interface AccountJpaRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
