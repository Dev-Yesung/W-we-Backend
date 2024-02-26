package wave.domain.rank.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import wave.domain.rank.domain.entity.PopularChart;
import wave.domain.rank.domain.vo.ChartType;

public record PopularChartDto(
	Long chartId,
	String chartName,
	String fromTime,
	String toTime,
	List<PopularPostDto> popularPosts
) {
	public static PopularChartDto of(
		PopularChart popularChart,
		List<PopularPostDto> popularPosts
	) {
		Long chartId = popularChart.getId();
		ChartType chartType = popularChart.getChartType();
		String chartName = chartType.getChartName();

		LocalDateTime createdAt = popularChart.getCreatedAt();
		if (chartType.equals(ChartType.MONTHLY)) {
			String fromTime = createdAt.minusMonths(1).plusDays(1)
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00"));
			String toTime = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 22:00"));

			return new PopularChartDto(chartId, chartName, fromTime, toTime, popularPosts);
		} else if (chartType.equals(ChartType.WEEKLY)) {
			String fromTime = createdAt.minusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00"));
			String toTime = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 22:00"));

			return new PopularChartDto(chartId, chartName, fromTime, toTime, popularPosts);
		} else {
			String fromTime = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00"));
			String toTime = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd 18:00"));

			return new PopularChartDto(chartId, chartName, fromTime, toTime, popularPosts);
		}
	}
}
