package wave.domain.certification.adapter.out;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.vo.Certification;
import wave.domain.mail.domain.port.out.SendMailPort;
import wave.domain.mail.infra.MailSender;
import wave.global.common.CommonAdapter;

@RequiredArgsConstructor
@CommonAdapter
public class MailSendAdapter implements SendMailPort {

	private final MailSender mailSender;

	@Override
	public void sendCertificationMail(Certification certification) {
		mailSender.sendCertificationCode(certification);
	}
}
