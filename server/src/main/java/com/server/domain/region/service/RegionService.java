package com.server.domain.region.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.region.entity.Region;
import com.server.domain.region.repository.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    public List<Region> saveRegions(List<Region> regions) {
        return regionRepository.saveAll(regions);
    }

    public Region findRegionByEngName(String engName) {
        if (engName == null) {
            return null;
        }

        return regionRepository.findByEngName(engName);
    }
}
