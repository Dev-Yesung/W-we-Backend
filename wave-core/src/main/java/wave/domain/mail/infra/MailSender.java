package wave.domain.mail.infra;

import wave.domain.account.domain.vo.Certification;

public interface MailSender {

	void sendCertificationCode(Certification certification);
}
