package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testSaveAndFindById() {
        Category category = new Category();
        category.setName("Bebidas");
        category.setActive(true);
        categoryRepository.save(category);

        Category found = categoryRepository.findById(category.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Bebidas", found.getName());
    }

    @Test
    void testFindByNameContaining() {
        Category category = new Category();
        category.setName("Produtos de Limpeza");
        category.setActive(true);
        categoryRepository.save(category);

        List<Category> result = categoryRepository.findByNameContaining("Limpeza");
        assertFalse(result.isEmpty());
        assertEquals("Produtos de Limpeza", result.get(0).getName());
    }

    @Test
    void testFindByActive() {
        Category active = new Category();
        active.setName("Ativa");
        active.setActive(true);
        categoryRepository.save(active);

        Category inactive = new Category();
        inactive.setName("Inativa");
        inactive.setActive(false);
        categoryRepository.save(inactive);

        List<Category> activeCategories = categoryRepository.findByActive(true);
        assertTrue(activeCategories.stream().anyMatch(c -> c.getName().equals("Ativa")));
    }
}
