package com.wave.batch.domain.rank.adapter.out.persistence;

import static wave.domain.media.domain.entity.QStreamingSession.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import wave.domain.rank.domain.port.out.persistence.StreamingSessionQueryRepository;
import wave.domain.rank.domain.vo.ChartType;
import wave.domain.rank.dto.StreamingSessionRankInfo;
import wave.global.utils.DateTimeUtils;

@Repository
public class StreamingSessionQueryDslRepository implements StreamingSessionQueryRepository {

	private final JPAQueryFactory queryFactory;

	public StreamingSessionQueryDslRepository(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Map<Long, StreamingSessionRankInfo> findAllByDateAndLimit(
		LocalDateTime dateTime, int limit, ChartType type) {
		LocalDateTime from = DateTimeUtils.getFromByChartType(dateTime, type);
		LocalDateTime to = DateTimeUtils.getToByChartType(dateTime, type);

		List<Tuple> tuples = queryFactory
			.select(streamingSession.postId, streamingSession.count())
			.from(streamingSession)
			.where(streamingSession.createdAt.between(from, to))
			.groupBy(streamingSession.postId)
			.limit(limit)
			.fetch();

		return tuples.stream()
			.map(tuple -> {
				Long postId = tuple.get(streamingSession.postId);
				Long size = tuple.get(streamingSession.count());

				return new StreamingSessionRankInfo(postId, size);
			})
			.collect(Collectors.toUnmodifiableMap(StreamingSessionRankInfo::postId,
				streamingSessionRankInfo -> streamingSessionRankInfo));
	}

}
