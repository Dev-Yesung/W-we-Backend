package wave.domain.auth.infra;

import java.util.Optional;

import wave.domain.mail.CertificationType;

public interface UserCertificationRepository {
	boolean existCertificationCodeByEmailAndType(CertificationType type, String email);

	Optional<String> getAndDeleteCertificationCodeByEmailAndType(CertificationType type, String email);

	void saveCertificationCodeByEmailAndType(CertificationType type, String email, String code);
}
