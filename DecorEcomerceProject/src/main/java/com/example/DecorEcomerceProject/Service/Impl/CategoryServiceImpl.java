package com.example.DecorEcomerceProject.Service.Impl;

import com.example.DecorEcomerceProject.Entities.Category;
import com.example.DecorEcomerceProject.Repositories.CategoryRepository;
import com.example.DecorEcomerceProject.Service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        //log.info("Fetching all categories");
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryByID(long id) {
        return categoryRepository.findById(id);
    }


    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        if(category == null){
            return "Not found category with id:  " +id;
        }else{
            categoryRepository.delete(category);
            return "Category "+id+ " has been deleted!";
        }
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category categoryExisted = categoryRepository.findById(id).get();
        categoryExisted.setName(category.getName());
        categoryRepository.save(categoryExisted);
        return categoryExisted;
    }
}
