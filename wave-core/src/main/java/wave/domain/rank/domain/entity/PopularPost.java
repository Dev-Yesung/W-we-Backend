package wave.domain.rank.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wave.global.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "popular_posts")
@Entity
public class PopularPost extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long postId;
	private String title;
	private Double score;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chart_id")
	private PopularChart popularChart;

	public PopularPost(
		Long postId,
		String title,
		Double score,
		PopularChart popularChart
	) {
		this.postId = postId;
		this.title = title;
		this.score = score;
		this.popularChart = popularChart;
	}

}
