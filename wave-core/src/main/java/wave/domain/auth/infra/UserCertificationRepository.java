package wave.domain.auth.infra;

import java.util.Optional;

public interface UserCertificationRepository {
	Optional<String> findCertificationNumberByEmail(String email);
}
