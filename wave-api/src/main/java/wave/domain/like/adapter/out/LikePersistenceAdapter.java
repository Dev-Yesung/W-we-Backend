package wave.domain.like.adapter.out;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.like.adapter.out.persistence.LikeJpaRepository;
import wave.domain.like.domain.entity.Like;
import wave.domain.like.domain.port.UpdateLikePort;
import wave.domain.like.dto.LikeUpdateInfo;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.post.adapter.out.persistence.PostJpaRepository;
import wave.domain.post.domain.entity.Post;
import wave.domain.post.dto.response.PostDeleteResponse;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

@RequiredArgsConstructor
@PersistenceAdapter
public class LikePersistenceAdapter implements UpdateLikePort {

	private final LikeJpaRepository likeJpaRepository;
	private final PostJpaRepository postJpaRepository;

	@Override
	public LikeUpdateInfo updateLikes(final LikeUpdateRequest likeUpdateRequest) {
		final long postId = likeUpdateRequest.postId();
		final User user = likeUpdateRequest.user();
		final Long userId = user.getId();

		Optional<Like> optionalLike = likeJpaRepository.findByPostIdAndUserId(postId, userId);
		if (optionalLike.isPresent()) {
			Like removedLike = optionalLike.get();
			removedLike.remove(user);

			return new LikeUpdateInfo(removedLike, true);
		}

		Post post = postJpaRepository.findById(postId)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_POST));
		Like newLike = new Like(post, user);
		newLike.addToPost(user);

		return new LikeUpdateInfo(newLike, false);
	}

	@Override
	public void deleteAllByPostId(PostDeleteResponse message) {
		long postId = message.postId();
		likeJpaRepository.deleteAllByPostId(postId);
	}

}
