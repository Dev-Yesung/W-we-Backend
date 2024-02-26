package wave.domain.rank.domain.port.out;

import java.time.LocalDateTime;
import java.util.Map;

import wave.domain.rank.domain.vo.ChartType;
import wave.domain.rank.dto.LikeRankInfo;
import wave.domain.rank.dto.StreamingSessionRankInfo;

public interface LoadRankPort {

	Map<Long, LikeRankInfo> getTopLikeByDateAndLimitAndChartType(
		LocalDateTime dateTime, int limit, ChartType type);

	Map<Long, StreamingSessionRankInfo> getTopStreamingByDateAndLimitChartType(
		LocalDateTime dateTime, int limit, ChartType type);

}
