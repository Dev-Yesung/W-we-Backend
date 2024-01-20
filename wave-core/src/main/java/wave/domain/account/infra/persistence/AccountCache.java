package wave.domain.account.infra.persistence;

import java.util.Optional;

import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;

public interface AccountCache {

	int cacheCertificationCode(Certification certification);

	Optional<String> getCertificationCode(Certification certification);

	Optional<String> getCertificationCode(CertificationType certificationType, String email);

	Optional<String> removeCertificationCode(Certification certification);

	boolean existCertificationCode(CertificationType certificationType, String email);
}
