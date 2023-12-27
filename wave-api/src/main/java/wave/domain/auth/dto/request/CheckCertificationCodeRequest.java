package wave.domain.auth.dto.request;

public record CheckCertificationCodeRequest(
	String email,
	String type
) {
}
