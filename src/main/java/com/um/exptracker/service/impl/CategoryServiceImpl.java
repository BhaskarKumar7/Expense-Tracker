package com.um.exptracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.um.exptracker.dto.CategoryDto;
import com.um.exptracker.dto.ServerResponseDto;
import com.um.exptracker.entity.CategoryEntity;
import com.um.exptracker.exception.CategoryNotFoundException;
import com.um.exptracker.repository.CategoryRepository;
import com.um.exptracker.service.CategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepo;
	
	@Override
	public ServerResponseDto createCategory(String categoryName) {
		
		if(!doesCategoryExists(categoryName)) {
			CategoryEntity categoryEntity = new CategoryEntity();
			categoryEntity.setCategoryName(categoryName.toUpperCase());
			categoryRepo.save(categoryEntity);
			return new ServerResponseDto("category added successfully", HttpStatus.OK.name());
		}
		else {
			return new ServerResponseDto("category already exist", HttpStatus.BAD_REQUEST.name());
		}
	}

	private boolean doesCategoryExists(String categoryName) {
		return categoryRepo.findByCategoryName(categoryName) == null ? false : true;
	}


	@Override
	public List<CategoryDto> fetchCategoriesByType(String categoryType) {
		List<CategoryEntity> categoryEntitiesList = categoryRepo.findByCategoryType(categoryType);
		List<CategoryDto> categoryDtosList = new ArrayList<>();
		categoryEntitiesList.forEach(ctEn -> {
			CategoryDto cDto = new CategoryDto();
			cDto.setCategoryId(ctEn.getCategoryId());
			cDto.setCategoryName(ctEn.getCategoryName());
			categoryDtosList.add(cDto);
		});
		return categoryDtosList;
	}

	@Override
	public String fetchCategoryNameById(Long id) {
		Optional<CategoryEntity> optCat = categoryRepo.findById(id);
		if(!optCat.isEmpty()) {
			return optCat.get().getCategoryName();	
		}
		
		throw new CategoryNotFoundException("No category found with the given id");
	}

}
