package wave.domain.account.domain;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;

public interface AccountUpdatePort {
	void publishCertificationEvent(Certification certification);

	User saveAccount(User user);

	void publishNewAccountEvent(User user);

	int cacheCertificationCode(Certification certification);

	void removeCertificationCode(Certification certification);
}
