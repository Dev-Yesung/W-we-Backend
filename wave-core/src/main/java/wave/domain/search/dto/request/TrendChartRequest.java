package wave.domain.search.dto.request;

import java.time.LocalDateTime;

import wave.domain.chart.domain.vo.ChartType;

public record TrendChartRequest(
	LocalDateTime requestDateTime,
	ChartType chartType
) {

	public static TrendChartRequest of(Integer year, Integer month, Integer day, String type) {
		LocalDateTime requestDateTime = LocalDateTime.of(year, month, day, 0, 0);
		ChartType chartType = ChartType.findByName(type);

		return new TrendChartRequest(requestDateTime, chartType);
	}

}
