package wave.domain.auth.dto.response;

public record SignupResponse(
	String email,
	String accessToken,
	String refreshToken
) {
}
