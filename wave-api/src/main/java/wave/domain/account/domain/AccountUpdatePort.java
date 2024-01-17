package wave.domain.account.domain;

import wave.domain.user.domain.User;

public interface AccountUpdatePort {
	void publishCertificationEvent(Certification certification);

	User saveAccount(User user);

	void publishNewAccountEvent(User user);
}
