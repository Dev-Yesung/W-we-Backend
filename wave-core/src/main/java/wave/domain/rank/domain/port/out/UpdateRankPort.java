package wave.domain.rank.domain.port.out;

import wave.domain.rank.domain.entity.PopularChart;

public interface UpdateRankPort {

	PopularChart saveChart(PopularChart popularChart);

	void saveChartOnCache(PopularChart popularChart);

}
