package com.server.domain.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.server.domain.category.entity.Category;
import com.server.domain.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	public List<Category> saveCategories(List<Category> categories) {
		return categoryRepository.saveAll(categories);
	}
}
