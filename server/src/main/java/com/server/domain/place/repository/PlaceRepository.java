package com.server.domain.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.place.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
	// List<Place> findBy
}
