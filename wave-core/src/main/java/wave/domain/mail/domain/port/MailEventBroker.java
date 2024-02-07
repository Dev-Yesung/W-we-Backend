package wave.domain.mail.domain.port;

import wave.domain.mail.domain.vo.MailResult;

public interface MailEventBroker {

	void publishMailSendResult(MailResult message);
}
