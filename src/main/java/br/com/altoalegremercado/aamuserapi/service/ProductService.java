package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.ProductDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByStore(Long storeId);

    Product getProductById(Long id);

    Product createProduct(ProductDTO productDTO) throws Exception;

    Product updateProduct(Long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(Long id) throws Exception;
}
