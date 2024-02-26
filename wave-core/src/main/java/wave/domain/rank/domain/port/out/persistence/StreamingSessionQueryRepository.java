package wave.domain.rank.domain.port.out.persistence;

import java.time.LocalDateTime;
import java.util.Map;

import wave.domain.rank.domain.vo.ChartType;
import wave.domain.rank.dto.StreamingSessionRankInfo;

public interface StreamingSessionQueryRepository {

	Map<Long, StreamingSessionRankInfo> findAllByDateAndLimit(LocalDateTime dateTime, int limit, ChartType type);

}
