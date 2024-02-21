package wave.domain.post.dto.response;

import wave.domain.post.domain.entity.Post;

public record PostDeleteResponse(
	long postId,
	Long userId
) {

	public static PostDeleteResponse of(Post post) {
		Long postId = post.getId();
		Long userId = post.getUser().getId();

		return new PostDeleteResponse(postId, userId);
	}

}
