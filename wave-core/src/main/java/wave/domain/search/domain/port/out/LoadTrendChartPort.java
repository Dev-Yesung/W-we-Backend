package wave.domain.search.domain.port.out;

import java.time.LocalDateTime;

import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.TrendChartDto;

public interface LoadTrendChartPort {

	TrendChartDto findTrendChartByDateTimeAndType(LocalDateTime dateTime, ChartType chartType);

}
