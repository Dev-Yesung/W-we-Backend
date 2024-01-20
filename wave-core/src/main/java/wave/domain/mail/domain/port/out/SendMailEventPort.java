package wave.domain.mail.domain.port.out;

import wave.domain.account.domain.vo.Certification;

public interface SendMailEventPort {

	void publishCertificationMailSuccessMessage(Certification certification);
}
