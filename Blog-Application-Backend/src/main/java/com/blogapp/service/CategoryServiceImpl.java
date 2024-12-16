package com.blogapp.service;

import com.blogapp.dto.CategoryDTO;
import com.blogapp.entity.Category;
import com.blogapp.exception.ResourceNotFoundException;
import com.blogapp.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category addedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(addedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO) {
        Category category = this.categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category ", "Category Id", categoryId));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category updateCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(updateCategory, CategoryDTO.class);

    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category ", "Category Id", categoryId));
        this.categoryRepository.delete(category);

    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category ", "Category Id", categoryId));
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map((category) -> this.modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return categoryDTOS;
    }
}
