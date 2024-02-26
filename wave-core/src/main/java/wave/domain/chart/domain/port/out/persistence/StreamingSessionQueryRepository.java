package wave.domain.chart.domain.port.out.persistence;

import java.time.LocalDateTime;
import java.util.Map;

import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.StreamingSessionRankInfo;

public interface StreamingSessionQueryRepository {

	Map<Long, StreamingSessionRankInfo> findAllByDateAndLimit(LocalDateTime dateTime, int limit, ChartType type);

}
