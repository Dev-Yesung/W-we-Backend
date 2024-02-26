package wave.domain.rank.domain.port.out.persistence;

public interface PopularChartCacheRepository {

	void save(String key, String value);

}
