package wave.domain.account.domain.port.out;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;

public interface UpdateAccountPort {

	User saveAccount(User user);

	int cacheCertificationCode(Certification certification);

	void removeCertificationCode(Certification certification);

}
