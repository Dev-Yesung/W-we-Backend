package com.wave.batch.domain.rank.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import wave.domain.rank.domain.entity.PopularChart;

public interface PopularChartJpaRepository extends JpaRepository<PopularChart, Long> {
}
