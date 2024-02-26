package com.wave.batch.domain.chart.adapter.out.persistence;

import static wave.domain.like.domain.entity.QLike.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import wave.domain.like.domain.entity.QLike;
import wave.domain.chart.domain.port.out.persistence.LikeQueryRepository;
import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.LikeRankInfo;
import wave.global.utils.DateTimeUtils;

@Repository
public class LikeQueryDslRepository implements LikeQueryRepository {

	private final JPAQueryFactory queryFactory;

	public LikeQueryDslRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Map<Long, LikeRankInfo> findAllByDateAndLimit(LocalDateTime dateTime, int limit, ChartType type) {
		LocalDateTime from = DateTimeUtils.getFromByChartType(dateTime, type);
		LocalDateTime to =  DateTimeUtils.getToByChartType(dateTime, type);

		List<Tuple> tuples = queryFactory.select(
				like.post.id,
				like.post.postContent.title,
				like.count()
			).from(like)
			.fetchJoin()
			.on(like.id.eq(like.post.id))
			.where(like.createdAt.between(from, to))
			.groupBy(like.post.id)
			.limit(limit)
			.fetch();

		return tuples.stream()
			.map(tuple -> {
				Long postId = tuple.get(QLike.like.post.id);
				String title = tuple.get(QLike.like.post.postContent.title);
				Long size = tuple.get(QLike.like.count());

				return new LikeRankInfo(postId, title, size);
			})
			.collect(Collectors.toUnmodifiableMap(LikeRankInfo::postId, likeRankInfo -> likeRankInfo));
	}

}
