package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Product;
import br.com.altoalegremercado.aamuserapi.domain.model.Promotion;
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
public class PromotionRepositoryTest {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    private Product createProduct() {
        Store store = new Store();
        store.setName("Loja");
        store.setActive(true);
        storeRepository.save(store);

        Product product = new Product();
        product.setName("Produto");
        product.setPrice(new BigDecimal("50.00"));
        product.setStore(store);
        return productRepository.save(product);
    }

    @Test
    void testSaveAndFindById() {
        Product product = createProduct();

        Promotion promotion = new Promotion();
        promotion.setProduct(product);
        promotion.setPromotionalPrice(new BigDecimal("39.90"));
        promotion.setActive(true);
        promotionRepository.save(promotion);

        Promotion found = promotionRepository.findById(promotion.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(0, new BigDecimal("39.90").compareTo(found.getPromotionalPrice()));
    }

    @Test
    void testFindByProductId() {
        Product product = createProduct();

        Promotion promotion = new Promotion();
        promotion.setProduct(product);
        promotion.setPromotionalPrice(new BigDecimal("29.90"));
        promotion.setActive(true);
        promotionRepository.save(promotion);

        List<Promotion> result = promotionRepository.findByProductId(product.getId());
        assertFalse(result.isEmpty());
        assertEquals(0, new BigDecimal("29.90").compareTo(result.get(0).getPromotionalPrice()));
    }

    @Test
    void testFindByProductIdAndActive() {
        Product product = createProduct();

        Promotion active = new Promotion();
        active.setProduct(product);
        active.setPromotionalPrice(new BigDecimal("19.90"));
        active.setActive(true);
        promotionRepository.save(active);

        Promotion inactive = new Promotion();
        inactive.setProduct(product);
        inactive.setPromotionalPrice(new BigDecimal("9.90"));
        inactive.setActive(false);
        promotionRepository.save(inactive);

        List<Promotion> result = promotionRepository.findByProductIdAndActive(product.getId(), true);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getActive());
    }
}
