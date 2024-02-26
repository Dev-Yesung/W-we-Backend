package wave.domain.chart.domain.port.out.persistence;

import java.time.LocalDateTime;
import java.util.Map;

import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.LikeRankInfo;

public interface LikeQueryRepository {

	Map<Long, LikeRankInfo> findAllByDateAndLimit(LocalDateTime dateTime, int limit, ChartType type);

}
