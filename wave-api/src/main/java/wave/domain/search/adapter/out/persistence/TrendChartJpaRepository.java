package wave.domain.search.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wave.domain.chart.domain.entity.TrendChart;

public interface TrendChartJpaRepository extends JpaRepository<TrendChart, Long> {

	@Query("""
				SELECT tc FROM TrendChart AS tc
				JOIN FETCH TrendPost AS tp
				ON tc.id = tp.trendChart.id
				WHERE tc.createdAt >= :from
					AND tc.createdAt <= :to
					AND tc.chartType = :name
		""")
	Optional<TrendChart> findByDateTimeAndType(
		@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("name") String name);

}
