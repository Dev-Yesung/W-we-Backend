package wave.domain.chart.domain.port.out;

import java.time.LocalDateTime;
import java.util.Map;

import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.LikeRankInfo;
import wave.domain.chart.dto.StreamingSessionRankInfo;

public interface LoadChartPort {

	Map<Long, LikeRankInfo> getTopLikeByDateAndLimitAndChartType(
		LocalDateTime dateTime, int limit, ChartType type);

	Map<Long, StreamingSessionRankInfo> getTopStreamingByDateAndLimitChartType(
		LocalDateTime dateTime, int limit, ChartType type);

}
