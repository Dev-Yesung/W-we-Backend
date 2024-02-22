package wave.domain.certification.application;

import org.springframework.kafka.annotation.KafkaListener;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.vo.Certification;
import wave.domain.mail.domain.port.out.SendMailEventPort;
import wave.domain.mail.domain.port.out.SendMailPort;
import wave.global.common.UseCase;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EventException;

@RequiredArgsConstructor
@UseCase
public class CertificationMailService {

	private final SendMailPort sendMailPort;
	private final SendMailEventPort sendMailEventPort;

	@KafkaListener(
		topics = {"signup_certification", "login_certification"},
		groupId = "group_mail",
		containerFactory = "kafkaListenerContainerFactory"
	)
	public void sendCertificationCodeByType(Certification message) {
		Certification certification = getCertification(message);
		sendMailPort.sendCertificationMail(certification);
		publishSuccessMessage(certification);
	}

	private Certification getCertification(Object message) {
		if (message instanceof Certification certification) {
			return certification;
		}

		throw new EventException(ErrorCode.INVALID_MESSAGE_CASTING);
	}

	private void publishSuccessMessage(Certification certification) {
		sendMailEventPort.publishCertificationMailSuccessMessage(certification);
	}
}
