package wave.domain.account.dto.request;

import wave.domain.account.domain.vo.Certification;
import wave.domain.account.domain.vo.CertificationType;
import wave.global.utils.RandomCodeUtils;

public record NewCertificationRequest(
	String email,
	String typeName
) {

	public static Certification of(NewCertificationRequest request) {
		String typeName = request.typeName();
		CertificationType certificationType = CertificationType.getCertificationType(typeName);
		String email = request.email();
		String randomCode = RandomCodeUtils.createRandomCode();

		return new Certification(certificationType, email, randomCode);
	}
}
