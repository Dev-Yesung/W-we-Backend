package wave.domain.account.dto.response;

public record AccountResponse(
	long id,
	String email,
	String accessToken,
	String refreshToken
) {
}
