package org.example.elegant_ecommerce_backend_project.categories;

import org.example.elegant_ecommerce_backend_project.Dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);
    }

    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }


    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.orElse(null);
    }


    public boolean updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setCategoryName(categoryDto.getCategoryName());
            category.setDescription(categoryDto.getDescription());
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
