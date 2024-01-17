package wave.domain.account.dto.request;

public record CertificationVerifyRequest(
	String email,
	String typeName,
	String certificationCode
) {
}
