package wave.domain.search.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.port.out.LoadAccountPort;
import wave.domain.account.dto.AccountInfo;
import wave.domain.account.dto.response.AccountSearchResponse;
import wave.domain.post.domain.port.out.LoadPostPort;
import wave.domain.post.dto.response.PostResponse;
import wave.domain.search.dto.PostSearchResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@UseCase
public class SearchService {

	private final LoadAccountPort loadAccountPort;
	private final LoadPostPort loadPostPort;

	public AccountSearchResponse findAccountByEmailAndNickname(String keyword, Long userId, Pageable pageable) {
		if (keyword.contains("@")) {
			int atMarkIndex = keyword.indexOf("@");
			keyword = keyword.substring(0, atMarkIndex);
		}

		Slice<AccountInfo> accountInfos = loadAccountPort
			.findByEmailAndNicknameExceptSelf(keyword, userId, pageable);

		return AccountSearchResponse.of(accountInfos);
	}

	public PostSearchResponse findPostByArtistName(String artistName, Pageable pageable) {
		Slice<PostResponse> postResponses = loadPostPort
			.findAllByArtistNameOrderByLikeSizeDesc(artistName, pageable);

		return PostSearchResponse.of(postResponses);
	}

	public PostSearchResponse findPostBySongTitle(String title, Pageable pageable) {
		Slice<PostResponse> postResponses = loadPostPort.findAllBySongTitle(title, pageable);

		return PostSearchResponse.of(postResponses);
	}

}
