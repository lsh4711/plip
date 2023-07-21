package com.server.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByCode(String code);
}
