package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.ProductDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Category;
import br.com.altoalegremercado.aamuserapi.domain.model.Product;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import br.com.altoalegremercado.aamuserapi.repository.CategoryRepository;
import br.com.altoalegremercado.aamuserapi.repository.ProductRepository;
import br.com.altoalegremercado.aamuserapi.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              StoreRepository storeRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getProductsByStore(Long storeId) {
        return productRepository.findByStoreId(storeId);
    }

    @Override
    public List<Product> getProductsByStoreAndCategory(Long storeId, Long categoryId) {
        return productRepository.findByStoreIdAndCategoryId(storeId, categoryId);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product createProduct(ProductDTO dto) throws Exception {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new Exception("Store with ID (" + dto.getStoreId() + ") not found!"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSku(dto.getSku());
        product.setActive(dto.getActive() != null ? dto.getActive() : true);
        product.setStore(store);

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new Exception("Category with ID (" + dto.getCategoryId() + ") not found!"));
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO dto) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product with ID (" + id + ") not found!"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSku(dto.getSku());
        product.setActive(dto.getActive());

        if (dto.getStoreId() != null) {
            Store store = storeRepository.findById(dto.getStoreId())
                    .orElseThrow(() -> new Exception("Store with ID (" + dto.getStoreId() + ") not found!"));
            product.setStore(store);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new Exception("Category with ID (" + dto.getCategoryId() + ") not found!"));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        if (!productRepository.findById(id).isPresent()) {
            throw new Exception("Product with ID (" + id + ") not found!");
        }
        productRepository.deleteById(id);
    }
}
