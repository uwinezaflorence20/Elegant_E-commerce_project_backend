package org.example.elegant_ecommerce_backend_project.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.elegant_ecommerce_backend_project.Dto.ProductDto;
import org.example.elegant_ecommerce_backend_project.categories.Category;
import org.example.elegant_ecommerce_backend_project.categories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;

    public Product createProduct(ProductDto productDto) throws IOException {
        Product product = new Product();

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setSize(productDto.getSize());
        product.setColor(productDto.getColor());

        Long categoryId = productDto.getCategoryId();
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
            product.setCategory(category);
        }

        MultipartFile image = productDto.getImage();
        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("secure_url").toString();
            product.setImageUrl(imageUrl);
        }

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, ProductDto productDto) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        existingProduct.setTitle(productDto.getTitle());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setSize(productDto.getSize());
        existingProduct.setColor(productDto.getColor());

        Long categoryId = productDto.getCategoryId();
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + categoryId));
            existingProduct.setCategory(category);
        }

        MultipartFile image = productDto.getImage();
        if (image != null && !image.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("secure_url").toString();
            existingProduct.setImageUrl(imageUrl);
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    public List<Product> searchProducts(String title, Long categoryId) {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream()
                .filter(product -> {
                    boolean matchesTitle = (title == null || product.getTitle().toLowerCase().contains(title.toLowerCase()));
                    boolean matchesCategory = (categoryId == null || (product.getCategory() != null && product.getCategory().getId().equals(categoryId)));
                    return matchesTitle && matchesCategory;
                })
                .collect(Collectors.toList());
    }
}
