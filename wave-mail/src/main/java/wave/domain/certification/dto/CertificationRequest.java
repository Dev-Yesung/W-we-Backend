package wave.domain.certification.dto;

public record CertificationRequest(
	String email,
	String certificationType
) {
}
