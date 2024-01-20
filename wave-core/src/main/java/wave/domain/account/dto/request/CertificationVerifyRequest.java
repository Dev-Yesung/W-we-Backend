package wave.domain.account.dto.request;

import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;

public record CertificationVerifyRequest(
	String email,
	String typeName,
	String certificationCode
) {
	public static Certification of(CertificationVerifyRequest request) {
		String typeName = request.typeName();
		CertificationType certificationType = CertificationType.getCertificationType(typeName);
		String email = request.email();
		String certificationCode = request.certificationCode();

		return new Certification(certificationType, email, certificationCode);
	}
}
