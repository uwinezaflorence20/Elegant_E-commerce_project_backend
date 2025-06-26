package org.example.elegant_ecommerce_backend_project.categories;

import org.example.elegant_ecommerce_backend_project.Dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")  // Proper base path for categories
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
        return ResponseEntity.ok("Category created successfully");
    }

    // List all categories
    @GetMapping
    public ResponseEntity<List<Category>> listCategory() {
        List<Category> categories = categoryService.listCategory();
        return ResponseEntity.ok(categories);
    }

    // Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    // Update category by id
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDTO) {
        boolean updated = categoryService.updateCategory(id, categoryDTO);
        if (updated) {
            return ResponseEntity.ok("Category updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    // Delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return ResponseEntity.ok("Category deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
