package wave.domain.mail.dto.request;

import wave.domain.mail.domain.vo.CertificationMail;

public record CertificationMailRequest(
	String email,
	String certificationType
) {

	public static CertificationMail of(CertificationMailRequest request) {
		String email = request.email();
		String certificationType = request.certificationType();

		return new CertificationMail(email, certificationType);
	}
}
