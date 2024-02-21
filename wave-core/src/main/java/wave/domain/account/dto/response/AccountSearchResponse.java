package wave.domain.account.dto.response;

import java.util.List;

import org.springframework.data.domain.Slice;

import wave.domain.account.dto.AccountInfo;

public record AccountSearchResponse(
	List<AccountInfo> accounts,
	boolean hasNext
) {
	public static AccountSearchResponse of(Slice<AccountInfo> accountInfos) {
		List<AccountInfo> list = accountInfos.stream()
			.toList();
		boolean hasNext = accountInfos.hasNext();

		return new AccountSearchResponse(list, hasNext);
	}
}
