package com.daengdaeng_eodiga.project.region.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daengdaeng_eodiga.project.region.entity.RegionVisitTotal;

public interface RegionVisitTotalRepository extends JpaRepository<RegionVisitTotal, Integer> {
	Optional<RegionVisitTotal> findByCityAndCityDetailAndUserId(String city, String cityDetail, int userId);

	List<RegionVisitTotal> findByUserId(int userId);
}
