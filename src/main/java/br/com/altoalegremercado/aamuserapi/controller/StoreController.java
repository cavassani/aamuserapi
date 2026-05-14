package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.controller.dto.ProductDTO;
import br.com.altoalegremercado.aamuserapi.controller.dto.StoreDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Product;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import br.com.altoalegremercado.aamuserapi.service.ProductService;
import br.com.altoalegremercado.aamuserapi.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;
    private final ProductService productService;

    @Autowired
    public StoreController(StoreService storeService, ProductService productService) {
        this.storeService = storeService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Store>> listStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Store store = storeService.getStoreById(id);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(store);
    }

    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody @Valid StoreDTO storeDTO) {
        Store store = storeService.createStore(storeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(store);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody @Valid StoreDTO storeDTO) {
        try {
            Store store = storeService.updateStore(id, storeDTO);
            return ResponseEntity.ok(store);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        try {
            storeService.deleteStore(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{storeId}/products")
    public ResponseEntity<List<Product>> listProductsByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(productService.getProductsByStore(storeId));
    }

    @PostMapping("/{storeId}/products")
    public ResponseEntity<Product> createProduct(@PathVariable Long storeId, @RequestBody @Valid ProductDTO productDTO) {
        productDTO.setStoreId(storeId);
        try {
            Product product = productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
