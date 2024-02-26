package com.wave.batch.domain.rank.adapter.out;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wave.batch.domain.rank.adapter.out.persistence.PopularChartJpaRepository;

import lombok.RequiredArgsConstructor;
import wave.domain.rank.domain.entity.PopularChart;
import wave.domain.rank.domain.port.out.LoadRankPort;
import wave.domain.rank.domain.port.out.UpdateRankPort;
import wave.domain.rank.domain.port.out.persistence.LikeQueryRepository;
import wave.domain.rank.domain.port.out.persistence.PopularChartCacheRepository;
import wave.domain.rank.domain.port.out.persistence.StreamingSessionQueryRepository;
import wave.domain.rank.domain.vo.ChartType;
import wave.domain.rank.dto.LikeRankInfo;
import wave.domain.rank.dto.PopularChartDto;
import wave.domain.rank.dto.PopularPostDto;
import wave.domain.rank.dto.StreamingSessionRankInfo;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;

@RequiredArgsConstructor
@PersistenceAdapter
public class RankPersistenceAdapter implements LoadRankPort, UpdateRankPort {

	private final PopularChartJpaRepository popularChartJpaRepository;
	private final PopularChartCacheRepository popularChartCacheRepository;
	private final LikeQueryRepository likeQueryRepository;
	private final StreamingSessionQueryRepository streamingSessionQueryRepository;

	private final ObjectMapper objectMapper;

	@Override
	public Map<Long, LikeRankInfo> getTopLikeByDateAndLimitAndChartType(
		LocalDateTime dateTime, int limit, ChartType type) {

		return likeQueryRepository.findAllByDateAndLimit(dateTime, limit, type);
	}

	@Override
	public Map<Long, StreamingSessionRankInfo> getTopStreamingByDateAndLimitChartType(
		LocalDateTime dateTime, int limit, ChartType type) {

		return streamingSessionQueryRepository.findAllByDateAndLimit(dateTime, limit, type);
	}

	@Override
	public PopularChart saveChart(PopularChart popularChart) {
		return popularChartJpaRepository.save(popularChart);
	}

	@Override
	public void saveChartOnCache(PopularChart popularChart) {
		List<PopularPostDto> popularPostDtos = popularChart.getPopularPosts()
			.stream()
			.map(PopularPostDto::of)
			.toList();
		PopularChartDto popularChartDto = PopularChartDto.of(popularChart, popularPostDtos);

		String key = popularChartDto.chartName() + popularChartDto.toTime();
		String value;
		try {
			value = objectMapper.writeValueAsString(popularChartDto);
		} catch (JsonProcessingException e) {
			throw new FileException(ErrorCode.NOT_FOUND_FILE, e);
		}

		popularChartCacheRepository.save(key, value);
	}
}
