package wave.domain.chart.dto;

import wave.domain.chart.domain.entity.TrendPost;

public record TrendPostDto(
	Long postId,
	String title,
	String score
) {

	public static TrendPostDto of(TrendPost trendPost) {
		Long postId = trendPost.getPostId();
		String title = trendPost.getTitle();
		Double score = trendPost.getScore();

		return null;
	}

}
