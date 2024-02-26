package wave.domain.rank.domain.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import wave.domain.rank.domain.vo.ChartType;
import wave.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rank_charts")
@Entity
public class PopularChart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ChartType chartType;

	@OneToMany(mappedBy = "popularChart",
		cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<PopularPost> popularPosts = new ArrayList<>();

	public PopularChart(
		ChartType chartType
	) {
		this.chartType = chartType;
	}

	public void addAllPopularPosts(Collection<PopularPost> popularPosts) {
		this.popularPosts.addAll(popularPosts);
	}
}
