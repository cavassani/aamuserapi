package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void testSaveAndFindById() {
        Store store = new Store();
        store.setName("Loja Teste");
        store.setCnpj("12345678901234");
        store.setActive(true);
        storeRepository.save(store);

        Store found = storeRepository.findById(store.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Loja Teste", found.getName());
    }

    @Test
    void testFindByCnpj() {
        Store store = new Store();
        store.setName("Loja XPTO");
        store.setCnpj("99887766554432");
        store.setActive(true);
        storeRepository.save(store);

        Store found = storeRepository.findByCnpj("99887766554432");
        assertNotNull(found);
        assertEquals("Loja XPTO", found.getName());
    }

    @Test
    void testFindByActive() {
        Store store1 = new Store();
        store1.setName("Loja Ativa");
        store1.setActive(true);
        storeRepository.save(store1);

        Store store2 = new Store();
        store2.setName("Loja Inativa");
        store2.setActive(false);
        storeRepository.save(store2);

        List<Store> activeStores = storeRepository.findByActive(true);
        assertTrue(activeStores.size() >= 1);
        assertTrue(activeStores.stream().anyMatch(s -> s.getName().equals("Loja Ativa")));
    }

    @Test
    void testFindByNameContaining() {
        Store store = new Store();
        store.setName("Mercado Central");
        store.setActive(true);
        storeRepository.save(store);

        List<Store> result = storeRepository.findByNameContaining("Central");
        assertFalse(result.isEmpty());
        assertEquals("Mercado Central", result.get(0).getName());
    }
}
