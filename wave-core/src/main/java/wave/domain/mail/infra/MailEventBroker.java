package wave.domain.mail.infra;

import wave.domain.mail.domain.vo.MailResult;

public interface MailEventBroker {

	void publishMailSendResult(MailResult message);
}
