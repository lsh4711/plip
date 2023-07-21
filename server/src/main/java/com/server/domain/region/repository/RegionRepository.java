package com.server.domain.region.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.region.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	Region findByEngName(String engRegion);
}
