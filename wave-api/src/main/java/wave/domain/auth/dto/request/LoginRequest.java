package wave.domain.auth.dto.request;

public record LoginRequest(
	String email,
	String certificationCode
) {
}
