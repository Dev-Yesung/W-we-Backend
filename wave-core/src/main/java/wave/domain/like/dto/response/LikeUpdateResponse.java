package wave.domain.like.dto.response;

import wave.domain.like.domain.entity.Like;

public record LikeUpdateResponse(
	long likeId,
	long userId,
	long postId,
	boolean status
) {

	public static LikeUpdateResponse of(Like like) {
		Long likeId = like.getId();
		Long userId = like.getUser().getId();
		Long postId = like.getPostId();
		boolean status = like.isStatus();

		return new LikeUpdateResponse(likeId, userId, postId, status);
	}

}
