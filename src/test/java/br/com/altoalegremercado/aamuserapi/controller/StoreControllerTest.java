package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.controller.dto.ProductDTO;
import br.com.altoalegremercado.aamuserapi.controller.dto.StoreDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Product;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import br.com.altoalegremercado.aamuserapi.service.ProductService;
import br.com.altoalegremercado.aamuserapi.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @MockBean
    private ProductService productService;

    @Test
    void testListStores() throws Exception {
        when(storeService.getAllStores()).thenReturn(Arrays.asList(new Store(), new Store()));

        mockMvc.perform(get("/api/stores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetStoreById() throws Exception {
        Store store = new Store();
        store.setId(1L);
        store.setName("Loja Teste");
        when(storeService.getStoreById(1L)).thenReturn(store);

        mockMvc.perform(get("/api/stores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Loja Teste"));
    }

    @Test
    void testGetStoreByIdNotFound() throws Exception {
        when(storeService.getStoreById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/stores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateStore() throws Exception {
        when(storeService.createStore(any(StoreDTO.class))).thenReturn(new Store());

        mockMvc.perform(post("/api/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Loja Teste\",\"cnpj\":\"11222333000181\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateStore() throws Exception {
        Store store = new Store();
        store.setId(1L);
        store.setName("Loja Atualizada");
        when(storeService.updateStore(eq(1L), any(StoreDTO.class))).thenReturn(store);

        mockMvc.perform(put("/api/stores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Loja Atualizada\",\"cnpj\":\"11222333000181\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Loja Atualizada"));
    }

    @Test
    void testUpdateStoreNotFound() throws Exception {
        when(storeService.updateStore(eq(99L), any(StoreDTO.class))).thenThrow(new Exception("not found"));

        mockMvc.perform(put("/api/stores/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Loja\",\"cnpj\":\"11222333000181\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteStore() throws Exception {
        mockMvc.perform(delete("/api/stores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteStoreNotFound() throws Exception {
        doThrow(new Exception("not found")).when(storeService).deleteStore(99L);

        mockMvc.perform(delete("/api/stores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListProductsByStore() throws Exception {
        when(productService.getProductsByStore(1L)).thenReturn(Arrays.asList(new Product(), new Product()));

        mockMvc.perform(get("/api/stores/1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testCreateProductInStore() throws Exception {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(new Product());

        mockMvc.perform(post("/api/stores/1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Produto Teste\",\"price\":10.50}"))
                .andExpect(status().isCreated());
    }
}
