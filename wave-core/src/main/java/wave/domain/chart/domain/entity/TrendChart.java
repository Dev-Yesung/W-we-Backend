package wave.domain.chart.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.domain.chart.domain.vo.ChartType;
import wave.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rank_charts")
@Entity
public class TrendChart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ChartType chartType;

	@OneToMany(mappedBy = "trendChart",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<TrendPost> trendPosts = new ArrayList<>();

	public TrendChart(
		ChartType chartType
	) {
		this.chartType = chartType;
	}

	public void addTrendPostsByTop50(PriorityQueue<TrendPost> trendPosts) {
		Stream.generate(trendPosts::poll)
			.limit(50)
			.forEach(this.trendPosts::add);
	}
}
