package com.um.exptracker.service;

import java.util.List;

import com.um.exptracker.dto.CategoryDto;
import com.um.exptracker.dto.ServerResponseDto;

public interface CategoryService {

	public ServerResponseDto createCategory(String categoryName);
	
	public List<CategoryDto> fetchCategoriesByType(String categoryType);
	
	public String fetchCategoryNameById(Long id);

}
