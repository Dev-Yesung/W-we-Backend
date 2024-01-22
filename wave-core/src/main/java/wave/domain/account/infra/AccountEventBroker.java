package wave.domain.account.infra;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;

public interface AccountEventBroker {

	void publishCertificationEvent(Certification certification);

	void publishNewAccountEvent(User user);
}
