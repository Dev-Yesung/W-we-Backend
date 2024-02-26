package wave.domain.rank.dto;

import wave.domain.rank.domain.entity.PopularPost;

public record PopularPostDto(
	Long postId,
	String title,
	String score
) {

	public static PopularPostDto of(PopularPost popularPost) {
		Long postId = popularPost.getPostId();
		String title = popularPost.getTitle();
		Double score = popularPost.getScore();

		return null;
	}

}
