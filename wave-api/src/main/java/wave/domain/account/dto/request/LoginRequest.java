package wave.domain.account.dto.request;

public record LoginRequest(
	String email,
	String certificationCode
) {
}
