package wave.domain.certification.infra;

import static java.nio.charset.StandardCharsets.*;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;
import wave.domain.mail.infra.mail.MailSender;
import wave.global.error.ErrorCode;
import wave.global.error.exception.BusinessException;

@RequiredArgsConstructor
@Component
public class SpringMailSender implements MailSender {

	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;

	@Override
	public void sendCertificationCode(Certification certification) {
		String email = certification.getEmail();
		CertificationType certificationType = certification.getType();
		String certificationCode = certification.getCertificationCode();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper
				= new MimeMessageHelper(mimeMessage, false, UTF_8.displayName());
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setSubject(certificationType.getName());
			mimeMessageHelper.setText(setContext(certificationCode, certificationType), true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new BusinessException(ErrorCode.FAIL_TO_SEND_EMAIL, e);
		}
	}

	private String setContext(final String certificationCode, final CertificationType certificationType) {
		String eventName = certificationType.getEventName();
		Context context = new Context();
		context.setVariable("certificationCode", certificationCode);

		return templateEngine.process(eventName, context);
	}
}
