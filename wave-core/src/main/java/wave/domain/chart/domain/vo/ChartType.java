package wave.domain.chart.domain.vo;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;

import lombok.Getter;
import wave.domain.chart.domain.entity.TrendChart;
import wave.domain.chart.domain.entity.TrendPost;
import wave.domain.chart.dto.LikeRankInfo;
import wave.domain.chart.dto.StreamingSessionRankInfo;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;

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

	public static PriorityQueue<TrendPost> calculatePostsRank(
		Map<Long, LikeRankInfo> likeInfos,
		Map<Long, StreamingSessionRankInfo> streamingInfos,
		TrendChart chart
	) {
		PriorityQueue<TrendPost> trendPosts = new PriorityQueue<>(
			(o1, o2) -> o2.getScore().compareTo(o1.getScore()));

		likeInfos.entrySet().stream()
			.parallel() // todo: 이 부분 성능테스트
			.map(entry -> {
				Long postId = entry.getKey();
				LikeRankInfo likeRankInfo = likeInfos.get(postId);
				String title = likeRankInfo.title();
				double likePoints = likeRankInfo.size() * LIKE_POINT;

				if (!streamingInfos.containsKey(postId)) {
					return new TrendPost(postId, title, likePoints, chart);
				}

				StreamingSessionRankInfo streamingInfo = streamingInfos.get(postId);
				double streamingPoints = streamingInfo.size() * STREAMING_POINT;
				double score = likePoints + streamingPoints;

				return new TrendPost(postId, title, score, chart);
			})
			.forEach(trendPosts::offer);

		return trendPosts;
	}

	public static ChartType findByName(String type) {
		return Arrays.stream(values())
			.filter(e -> e.chartName.equals(type))
			.findFirst()
			.orElseThrow(() -> new EntityException(ErrorCode.INVALID_TREND_CHART));
	}
}
