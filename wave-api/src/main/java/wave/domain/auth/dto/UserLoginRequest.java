package wave.domain.auth.dto;

public record UserLoginRequest(
	String email,
	String certificationNumber
) {
}
