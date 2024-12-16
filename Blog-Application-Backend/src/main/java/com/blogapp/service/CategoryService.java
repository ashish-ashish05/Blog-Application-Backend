package com.blogapp.service;

import com.blogapp.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {


    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Integer categoryId);
    CategoryDTO getCategory(Integer categoryId);
    List<CategoryDTO> getAllCategories();
}
