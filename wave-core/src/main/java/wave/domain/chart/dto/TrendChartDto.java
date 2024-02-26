package wave.domain.chart.dto;

import java.time.LocalDateTime;
import java.util.List;

import wave.domain.chart.domain.entity.TrendChart;
import wave.domain.chart.domain.vo.ChartType;
import wave.global.utils.DateTimeUtils;

public record TrendChartDto(
	Long chartId,
	String chartName,
	LocalDateTime from,
	LocalDateTime to,
	List<TrendPostDto> popularPosts
) {

	public static TrendChartDto of(
		TrendChart trendChart,
		List<TrendPostDto> trentPosts
	) {
		Long chartId = trendChart.getId();
		ChartType chartType = trendChart.getChartType();
		String chartName = chartType.getChartName();
		LocalDateTime dateTime = trendChart.getCreatedAt();
		LocalDateTime from = DateTimeUtils.getFromByChartType(dateTime, chartType);
		LocalDateTime to = DateTimeUtils.getToByChartType(dateTime, chartType);

		return new TrendChartDto(chartId, chartName, from, to, trentPosts);
	}

	public static TrendChartDto of(TrendChart trendChart) {
		Long chartId = trendChart.getId();
		String chartName = trendChart.getChartType().getChartName();
		List<TrendPostDto> trendPosts = trendChart.getTrendPosts()
			.stream()
			.map(TrendPostDto::of)
			.toList();
		ChartType chartType = trendChart.getChartType();
		LocalDateTime dateTime = trendChart.getCreatedAt();
		LocalDateTime from = DateTimeUtils.getFromByChartType(dateTime, chartType);
		LocalDateTime to = DateTimeUtils.getToByChartType(dateTime, chartType);

		return new TrendChartDto(chartId, chartName, from, to, trendPosts);
	}

}
