package com.waraujo.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waraujo.course.entities.ProductCategory;
import com.waraujo.course.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<ProductCategory> findAll() {
		return repository.findAll();
	}
	
	public ProductCategory findById(Long id) {
		Optional<ProductCategory> obj = repository.findById(id);
		return obj.get();
	}

}
