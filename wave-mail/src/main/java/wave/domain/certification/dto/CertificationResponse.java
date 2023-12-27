package wave.domain.certification.dto;

public record CertificationResponse(
	String email,
	long timeLimit
) {
}
