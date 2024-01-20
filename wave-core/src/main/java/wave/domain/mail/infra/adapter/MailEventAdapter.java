package wave.domain.mail.infra.adapter;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;
import wave.domain.mail.domain.port.out.SendMailEventPort;
import wave.domain.mail.domain.vo.MailResult;
import wave.domain.mail.infra.event.MailEventBroker;
import wave.global.common.EventAdapter;

@RequiredArgsConstructor
@EventAdapter
public class MailEventAdapter
	implements SendMailEventPort {

	private final MailEventBroker mailEventBroker;

	@Override
	public void publishCertificationMailSuccessMessage(Certification certification) {
		MailResult message = getMailResult(certification);
		mailEventBroker.publishMailSendResult(message);
	}

	private MailResult getMailResult(Certification certification) {
		String email = certification.getEmail();
		CertificationType type = certification.getType();
		String eventName = type.getEventName();

		return new MailResult(email, eventName, true);
	}
}
