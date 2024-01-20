package wave.domain.mail.infra.event;

import wave.domain.mail.domain.vo.MailResult;

public interface MailEventBroker {

	void publishMailSendResult(MailResult message);
}
