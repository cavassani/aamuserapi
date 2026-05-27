package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByStoreId(Long storeId);

    List<Product> findByNameContaining(String name);

    List<Product> findByActive(Boolean active);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByStoreIdAndCategoryId(Long storeId, Long categoryId);
}
