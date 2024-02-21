package wave.domain.account.dto;

import wave.domain.account.domain.entity.User;

public record AccountInfo(
	Long userId,
	String email,
	String nickname
) {

	public static AccountInfo of(User user) {
		Long userId = user.getId();
		String email = user.getEmail();
		String nickname = user.getNickname();

		return new AccountInfo(userId, email, nickname);
	}

}
