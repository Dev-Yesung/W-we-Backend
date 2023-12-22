package wave.domain.auth.infra;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class InmemoryUserCertificationRepository implements UserCertificationRepository {
	private static final Map<String, String> inmemoryCertificationNumberMap = new ConcurrentHashMap<>();

	@Override
	public Optional<String> findCertificationNumberByEmail(String email) {
		String certificationNumber = inmemoryCertificationNumberMap.get(email);

		return Optional.ofNullable(certificationNumber);
	}
}
