package wave.domain.chart.domain.port.out;

import wave.domain.chart.domain.entity.TrendChart;

public interface UpdateChartPort {

	TrendChart saveChart(TrendChart trendChart);

	void saveChartOnCache(TrendChart trendChart);

}
