package wave.domain.user.infra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
