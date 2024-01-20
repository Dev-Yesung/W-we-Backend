package wave.domain.account.domain;

import java.util.Optional;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;
import wave.domain.mail.CertificationType;

public interface AccountLoadPort {

	Optional<User> findAccountByEmail(String email);

	void checkDuplicateEmail(String email);

	String getCertificationCode(Certification certification);

	void existCertificationCode(CertificationType certificationType, String email);
}
