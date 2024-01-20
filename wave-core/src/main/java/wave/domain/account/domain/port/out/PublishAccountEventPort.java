package wave.domain.account.domain.port.out;

import wave.domain.account.domain.entity.User;
import wave.domain.account.domain.vo.Certification;

public interface PublishAccountEventPort {

	void publishCertificationEvent(Certification certification);

	void publishNewAccountEvent(User user);
}
