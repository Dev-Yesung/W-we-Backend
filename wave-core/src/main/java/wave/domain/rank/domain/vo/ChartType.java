package wave.domain.rank.domain.vo;

import java.util.Map;
import java.util.PriorityQueue;

import lombok.Getter;
import wave.domain.rank.domain.entity.PopularChart;
import wave.domain.rank.domain.entity.PopularPost;
import wave.domain.rank.dto.LikeRankInfo;
import wave.domain.rank.dto.StreamingSessionRankInfo;

@Getter
public enum ChartType {

	DAILY("DAILY_HOT_TREND"),
	WEEKLY("WEEKLY_HOT_TREND"),
	MONTHLY("MONTHLY_HOT_TREND");

	private static final double LIKE_POINT = 0.5;
	private static final double STREAMING_POINT = 1;

	private final String chartName;

	ChartType(String chartName) {
		this.chartName = chartName;
	}

	public static PriorityQueue<PopularPost> calculatePostsRank(
		Map<Long, LikeRankInfo> likeInfos,
		Map<Long, StreamingSessionRankInfo> streamingInfos,
		PopularChart chart
	) {
		PriorityQueue<PopularPost> popularPosts = new PriorityQueue<>(
			(o1, o2) -> o2.getScore().compareTo(o1.getScore()));

		likeInfos.entrySet().stream()
			.parallel() // todo: 이 부분 성능테스트
			.map(entry -> {
				Long postId = entry.getKey();
				LikeRankInfo likeRankInfo = likeInfos.get(postId);
				String title = likeRankInfo.title();
				double likePoints = likeRankInfo.size() * LIKE_POINT;

				if (!streamingInfos.containsKey(postId)) {
					return new PopularPost(postId, title, likePoints, chart);
				}

				StreamingSessionRankInfo streamingInfo = streamingInfos.get(postId);
				double streamingPoints = streamingInfo.size() * STREAMING_POINT;
				double score = likePoints + streamingPoints;

				return new PopularPost(postId, title, score, chart);
			})
			.forEach(popularPosts::offer);

		return popularPosts;
	}
}
