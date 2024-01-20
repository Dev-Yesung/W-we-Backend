package wave.domain.account.dto.request;

import wave.domain.account.domain.vo.Certification;
import wave.domain.mail.CertificationType;
import wave.global.utils.RandomCodeCreator;

public record NewCertificationRequest(
	String email,
	String typeName
) {

	public static Certification of(NewCertificationRequest request) {
		String typeName = request.typeName();
		CertificationType certificationType = CertificationType.getCertificationType(typeName);
		String email = request.email();
		String randomCode = RandomCodeCreator.createRandomCode();

		return new Certification(certificationType, email, randomCode);
	}
}
