package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Product;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void testSaveAndFindById() {
        Product product = new Product();
        product.setName("Produto Teste");
        product.setPrice(new BigDecimal("29.90"));
        productRepository.save(product);

        Product found = productRepository.findById(product.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Produto Teste", found.getName());
    }

    @Test
    void testFindByStoreId() {
        Store store = new Store();
        store.setName("Loja");
        store.setActive(true);
        storeRepository.save(store);

        Product product = new Product();
        product.setName("Produto da Loja");
        product.setStore(store);
        productRepository.save(product);

        List<Product> products = productRepository.findByStoreId(store.getId());
        assertFalse(products.isEmpty());
        assertEquals("Produto da Loja", products.get(0).getName());
    }

    @Test
    void testFindByNameContaining() {
        Product product = new Product();
        product.setName("Arroz Integral");
        productRepository.save(product);

        List<Product> result = productRepository.findByNameContaining("Arroz");
        assertFalse(result.isEmpty());
        assertEquals("Arroz Integral", result.get(0).getName());
    }

    @Test
    void testFindByActive() {
        Product active = new Product();
        active.setName("Produto Ativo");
        active.setActive(true);
        productRepository.save(active);

        Product inactive = new Product();
        inactive.setName("Produto Inativo");
        inactive.setActive(false);
        productRepository.save(inactive);

        List<Product> activeProducts = productRepository.findByActive(true);
        assertTrue(activeProducts.stream().anyMatch(p -> p.getName().equals("Produto Ativo")));
    }
}
