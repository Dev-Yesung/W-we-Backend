package wave.domain.account.dto.request;

public record SignupRequest(
	String email,
	String nickname
) {
}
