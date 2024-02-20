package wave.domain.like.adapter.out;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import wave.domain.account.domain.entity.User;
import wave.domain.like.adapter.out.persistence.LikeJpaRepository;
import wave.domain.like.domain.entity.Like;
import wave.domain.like.domain.port.UpdateLikePort;
import wave.domain.like.dto.request.LikeUpdateRequest;
import wave.domain.post.dto.response.PostDeleteResponse;
import wave.global.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class LikePersistenceAdapter implements UpdateLikePort {

	private final LikeJpaRepository likeJpaRepository;

	@Override
	public Like updateLikes(final LikeUpdateRequest likeUpdateRequest) {
		final long likeId = likeUpdateRequest.likeId();
		final User user = likeUpdateRequest.user();

		Optional<Like> optionalLike = likeJpaRepository.findById(likeId);
		optionalLike.ifPresent(like -> {
			like.isSameUser(user);
			like.changeStatus();
		});

		return optionalLike.orElseGet(() -> likeJpaRepository.save(new Like(likeId, user)));
	}

	@Override
	public void deleteAllByPostId(PostDeleteResponse message) {
		long postId = message.postId();
		likeJpaRepository.deleteAllByPostId(postId);
	}

}
