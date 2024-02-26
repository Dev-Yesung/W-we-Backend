package wave.domain.chart.domain.port.out.persistence;

import java.util.Optional;

public interface TrendChartCacheRepository {

	void save(String key, String value);

	Optional<String> get(String key);

}
