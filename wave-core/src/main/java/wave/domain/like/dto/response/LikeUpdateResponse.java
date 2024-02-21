package wave.domain.like.dto.response;

import wave.domain.like.dto.LikeUpdateInfo;

public record LikeUpdateResponse(
	long likeId,
	long userId,
	long postId,
	boolean isRemoved
) {

	public static LikeUpdateResponse of(LikeUpdateInfo info) {
		Long likeId = info.like().getId();
		Long userId = info.like().getUser().getId();
		Long postId = info.like().getPost().getId();
		boolean isRemoved = info.isRemoved();

		return new LikeUpdateResponse(likeId, userId, postId, isRemoved);
	}

}
