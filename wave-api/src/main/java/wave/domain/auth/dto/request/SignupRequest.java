package wave.domain.auth.dto.request;

public record SignupRequest(
	String email,
	String nickname,
	String certificationCode
) {
}
