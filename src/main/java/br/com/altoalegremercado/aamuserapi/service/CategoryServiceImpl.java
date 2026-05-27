package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.CategoryDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Category;
import br.com.altoalegremercado.aamuserapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setActive(dto.getActive() != null ? dto.getActive() : true);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO dto) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category with ID (" + id + ") not found!"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setActive(dto.getActive());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new Exception("Category with ID (" + id + ") not found!");
        }
        categoryRepository.deleteById(id);
    }
}
