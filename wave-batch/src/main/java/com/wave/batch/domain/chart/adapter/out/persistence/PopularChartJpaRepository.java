package com.wave.batch.domain.chart.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.chart.domain.entity.TrendChart;

public interface PopularChartJpaRepository extends JpaRepository<TrendChart, Long> {
}
