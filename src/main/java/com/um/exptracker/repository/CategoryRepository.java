package com.um.exptracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.um.exptracker.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	CategoryEntity findByCategoryName(String categoryName);
	
	/*
	 * @Query(value = "select * from exptracker_schema.category_entity limit 4",
	 * nativeQuery = true) List<CategoryEntity> findIncomeCategories();
	 * 
	 * @Query(value = "select * from exptracker_schema.category_entity offset 4",
	 * nativeQuery = true) List<CategoryEntity> findExpenseCategories();
	 */
	
	List<CategoryEntity> findByCategoryType(String categoryType);
}
