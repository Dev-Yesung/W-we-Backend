package wave.domain.search.adapter.out;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wave.domain.chart.domain.entity.TrendChart;
import wave.domain.chart.domain.port.out.persistence.TrendChartCacheRepository;
import wave.domain.chart.domain.vo.ChartType;
import wave.domain.chart.dto.TrendChartDto;
import wave.domain.search.adapter.out.persistence.TrendChartJpaRepository;
import wave.domain.search.domain.port.out.LoadTrendChartPort;
import wave.global.common.PersistenceAdapter;
import wave.global.error.ErrorCode;
import wave.global.error.exception.EntityException;
import wave.global.error.exception.FileException;
import wave.global.utils.DateTimeUtils;

@RequiredArgsConstructor
@PersistenceAdapter
public class TrendChartAdapter implements LoadTrendChartPort {

	private final TrendChartCacheRepository trendChartCacheRepository;
	private final TrendChartJpaRepository trendChartJpaRepository;
	private final ObjectMapper objectMapper;

	@Override
	public TrendChartDto findTrendChartByDateTimeAndType(LocalDateTime dateTime, ChartType chartType) {
		LocalDateTime from = DateTimeUtils.getFromByChartType(dateTime, chartType);
		String key = chartType.getChartName() + ":" + from;
		Optional<String> optionalValue = trendChartCacheRepository.get(key);

		if (optionalValue.isPresent()) {
			String value = optionalValue.get();
			try {
				return objectMapper.readValue(value, TrendChartDto.class);
			} catch (JsonProcessingException e) {
				throw new FileException(ErrorCode.NOT_FOUND_FILE, e);
			}
		}

		LocalDateTime to = DateTimeUtils.getToByChartType(dateTime, chartType);
		String name = chartType.name();
		TrendChart trendChart = trendChartJpaRepository.findByDateTimeAndType(from, to, name)
			.orElseThrow(() -> new EntityException(ErrorCode.NOT_FOUND_TREND_CHART));

		return TrendChartDto.of(trendChart);
	}

}
