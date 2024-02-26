package wave.global.utils;

import java.time.LocalDateTime;

import wave.domain.rank.domain.vo.ChartType;

public final class DateTimeUtils {

	public static LocalDateTime getFromByChartType(LocalDateTime dateTime, ChartType type) {
		if (type.equals(ChartType.MONTHLY)) {
			return dateTime.withDayOfMonth(1)
				.withHour(0)
				.withMinute(0)
				.withSecond(0);
		} else if (type.equals(ChartType.WEEKLY)) {
			return dateTime.minusDays(7)
				.withHour(0)
				.withMinute(0)
				.withSecond(0);
		} else {
			return dateTime.minusDays(1)
				.withHour(12)
				.withMinute(0)
				.withSecond(0);
		}
	}

	public static LocalDateTime getToByChartType(LocalDateTime dateTime, ChartType type) {
		if (type.equals(ChartType.MONTHLY) || type.equals(ChartType.WEEKLY)) {
			return dateTime.withHour(22)
				.withMinute(0)
				.withSecond(0);
		} else {
			return dateTime.withHour(12)
				.withMinute(0)
				.withSecond(0);
		}
	}
}
