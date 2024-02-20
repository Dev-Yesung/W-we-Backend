package wave.domain.like.application;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;
import wave.domain.like.domain.port.UpdateLikePort;
import wave.domain.like.domain.entity.Like;
import wave.domain.like.domain.port.PublishLikeEventPort;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.like.dto.response.LikeUpdateResponse;
import wave.domain.post.dto.response.PostDeleteResponse;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class LikeService {

	private final UpdateLikePort updateLikePort;
	private final PublishLikeEventPort publishLikeEventPort;

	public LikeUpdateResponse updateLikes(LikeUpdateRequest likeUpdateRequest) {
		Like updatedLike = updateLikePort.updateLikes(likeUpdateRequest);
		if (updatedLike.isStatus()) {
			publishLikeEventPort.publishLikeUpdateEvent(updatedLike);
		}

		return LikeUpdateResponse.of(updatedLike);
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void deleteAllComment(PostDeleteResponse message) {
		updateLikePort.deleteAllByPostId(message);
	}
}
