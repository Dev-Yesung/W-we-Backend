package wave.domain.chart.application;

import static wave.domain.chart.domain.vo.ChartType.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wave.domain.chart.domain.entity.TrendChart;
import wave.domain.chart.domain.entity.TrendPost;
import wave.domain.chart.domain.port.out.LoadChartPort;
import wave.domain.chart.domain.port.out.UpdateChartPort;
import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.LikeRankInfo;
import wave.domain.chart.dto.StreamingSessionRankInfo;
import wave.global.common.UseCase;

@RequiredArgsConstructor
@Transactional
@Async
@UseCase
public class RankService {

	private final LoadChartPort loadChartPort;
	private final UpdateChartPort updateChartPort;

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
			= loadChartPort.getTopLikeByDateAndLimitAndChartType(now, limit, chartType);
		Map<Long, StreamingSessionRankInfo> streamingInfos
			= loadChartPort.getTopStreamingByDateAndLimitChartType(now, limit, chartType);

		TrendChart newChart = new TrendChart(chartType);
		TrendChart saveChart = updateChartPort.saveChart(newChart);
		PriorityQueue<TrendPost> trendPosts
			= calculatePostsRank(likeInfos, streamingInfos, saveChart);
		saveChart.addTrendPostsByTop50(trendPosts);

		updateChartPort.saveChartOnCache(saveChart);
	}

}
