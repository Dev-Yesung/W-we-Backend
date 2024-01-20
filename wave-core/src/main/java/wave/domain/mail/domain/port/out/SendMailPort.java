package wave.domain.mail.domain.port.out;

import wave.domain.account.domain.vo.Certification;

public interface SendMailPort {

	void sendCertificationMail(Certification certification);
}
