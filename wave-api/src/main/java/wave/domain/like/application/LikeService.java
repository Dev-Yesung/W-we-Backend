package wave.domain.like.application;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.like.domain.port.PublishLikeEventPort;
import wave.domain.like.domain.port.UpdateLikePort;
import wave.domain.like.dto.LikeUpdateInfo;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.like.dto.response.LikeUpdateResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class LikeService {

	private final UpdateLikePort updateLikePort;
	private final PublishLikeEventPort publishLikeEventPort;

	public LikeUpdateResponse updateLikes(LikeUpdateRequest likeUpdateRequest) {
		LikeUpdateInfo likeUpdateInfo = updateLikePort.updateLikes(likeUpdateRequest);
		if (!likeUpdateInfo.isRemoved()) {
			publishLikeEventPort.publishLikeUpdateEvent(likeUpdateInfo.like());
		}

		return LikeUpdateResponse.of(likeUpdateInfo);
	}

}
