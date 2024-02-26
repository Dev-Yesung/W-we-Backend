package com.wave.batch.domain.chart.adapter.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wave.batch.domain.chart.adapter.out.persistence.PopularChartJpaRepository;

import lombok.RequiredArgsConstructor;
import wave.domain.chart.domain.entity.TrendChart;
import wave.domain.chart.domain.port.out.LoadChartPort;
import wave.domain.chart.domain.port.out.UpdateChartPort;
import wave.domain.chart.domain.port.out.persistence.LikeQueryRepository;
import wave.domain.chart.domain.port.out.persistence.TrendChartCacheRepository;
import wave.domain.chart.domain.port.out.persistence.StreamingSessionQueryRepository;
import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.LikeRankInfo;
import wave.domain.chart.dto.TrendChartDto;
import wave.domain.chart.dto.TrendPostDto;
import wave.domain.chart.dto.StreamingSessionRankInfo;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.FileException;

@RequiredArgsConstructor
@PersistenceAdapter
public class ChartPersistenceAdapter implements LoadChartPort, UpdateChartPort {

	private final PopularChartJpaRepository popularChartJpaRepository;
	private final TrendChartCacheRepository trendChartCacheRepository;
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
	public TrendChart saveChart(TrendChart trendChart) {
		return popularChartJpaRepository.save(trendChart);
	}

	@Override
	public void saveChartOnCache(TrendChart trendChart) {
		List<TrendPostDto> trendPostDtos = trendChart.getTrendPosts()
			.stream()
			.map(TrendPostDto::of)
			.toList();
		TrendChartDto trendChartDto = TrendChartDto.of(trendChart, trendPostDtos);

		String key = trendChartDto.chartName() + ":" + trendChartDto.from();
		String value;
		try {
			value = objectMapper.writeValueAsString(trendChartDto);
		} catch (JsonProcessingException e) {
			throw new FileException(ErrorCode.NOT_FOUND_FILE, e);
		}

		trendChartCacheRepository.save(key, value);
	}
}
