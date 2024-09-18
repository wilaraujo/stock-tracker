package com.waraujo.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waraujo.course.entities.ProductCategory;

public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {

}
