package com.wave.batch.domain.rank.application;

import static wave.domain.rank.domain.vo.ChartType.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.rank.domain.entity.PopularChart;
import wave.domain.rank.domain.entity.PopularPost;
import wave.domain.rank.domain.port.out.LoadRankPort;
import wave.domain.rank.domain.port.out.UpdateRankPort;
import wave.domain.rank.domain.vo.ChartType;
import wave.domain.rank.dto.LikeRankInfo;
import wave.domain.rank.dto.StreamingSessionRankInfo;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@Async
@UseCase
public class RankService {

	private final LoadRankPort loadRankPort;
	private final UpdateRankPort updateRankPort;

	@Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
	void rankDailyTop50PostAt18() {
		getPopularChartByType(100, DAILY);
	}

	@Scheduled(cron = "0 0 22 ? * SUN", zone = "Asia/Seoul")
	void rankWeeklyTop50PostAtSat22() {
		getPopularChartByType(100, WEEKLY);
	}

	@Scheduled(cron = "0 0 22 L * ?", zone = "Asia/Seoul")
	void rankMonthlyTop50PostOnLastDayOfMonthAt22() {
		getPopularChartByType(100, MONTHLY);
	}

	private void getPopularChartByType(int limit, ChartType chartType) {
		LocalDateTime now = LocalDateTime.now();
		Map<Long, LikeRankInfo> likeInfos
			= loadRankPort.getTopLikeByDateAndLimitAndChartType(now, limit, chartType);
		Map<Long, StreamingSessionRankInfo> streamingInfos
			= loadRankPort.getTopStreamingByDateAndLimitChartType(now, limit, chartType);

		PopularChart newChart = new PopularChart(chartType);
		PopularChart saveChart = updateRankPort.saveChart(newChart);
		PriorityQueue<PopularPost> popularPosts
			= calculatePostsRank(likeInfos, streamingInfos, saveChart);
		saveChart.addPopularPostsByTop50(popularPosts);

		updateRankPort.saveChartOnCache(saveChart);
	}

}
