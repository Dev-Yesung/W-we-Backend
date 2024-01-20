package wave.domain.certification.application;

import static java.nio.charset.StandardCharsets.*;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import wave.domain.account.infra.AccountCache;
import wave.global.utils.RandomCodeCreator;
import wave.domain.certification.dto.CertificationRequest;
import wave.domain.certification.dto.CertificationResponse;
import wave.domain.mail.CertificationType;

@RequiredArgsConstructor
@Transactional
@Service
public class CertificationMailService {
	private final RandomCodeCreator randomCodeCreator;
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	private final AccountCache userCertificationRepository;

	public CertificationResponse sendCertificationMailByCertificationType(final CertificationRequest request) {
		String certificationCode = randomCodeCreator.createRandomCode();
		saveCertificationCodeByEmailAndType(request, certificationCode);
		sendEmail(request, certificationCode);

		return getCertificationResponse(request);
	}

	private void saveCertificationCodeByEmailAndType(CertificationRequest request, String certificationCode) {
		String requestEmail = request.email();
		CertificationType certificationType = getCertificationType(request);
		userCertificationRepository.saveCertificationCodeByEmailAndType(
			certificationType, requestEmail, certificationCode);
	}

	private void sendEmail(CertificationRequest request, String certificationCode) {
		String email = request.email();
		CertificationType certificationType = getCertificationType(request);
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper mimeMessageHelper
				= new MimeMessageHelper(mimeMessage, false, UTF_8.displayName());
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setSubject(certificationType.getMailTitle());
			mimeMessageHelper.setText(setContext(certificationCode, certificationType), true);
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private CertificationResponse getCertificationResponse(CertificationRequest request) {
		String email = request.email();
		CertificationType certificationType = getCertificationType(request);
		long timeLimit = certificationType.getTimeLimit();
		return new CertificationResponse(email, timeLimit);
	}

	private CertificationType getCertificationType(final CertificationRequest request) {
		String requestCertificationType = request.certificationType();
		return CertificationType.getCertificationType(requestCertificationType);
	}

	private String setContext(final String certificationCode, final CertificationType certificationType) {
		String typeName = certificationType.getTypeName();
		Context context = new Context();
		context.setVariable("certificationCode", certificationCode);

		return templateEngine.process(typeName, context);
	}
}
