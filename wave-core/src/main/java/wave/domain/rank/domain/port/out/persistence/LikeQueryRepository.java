package wave.domain.rank.domain.port.out.persistence;

import java.time.LocalDateTime;
import java.util.Map;

import wave.domain.rank.domain.vo.ChartType;
import wave.domain.rank.dto.LikeRankInfo;

public interface LikeQueryRepository {

	Map<Long, LikeRankInfo> findAllByDateAndLimit(LocalDateTime dateTime, int limit, ChartType type);

}
