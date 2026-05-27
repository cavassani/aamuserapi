package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.controller.dto.PromotionDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Promotion;
import br.com.altoalegremercado.aamuserapi.service.PromotionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromotionController.class)
public class PromotionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromotionService promotionService;

    @Test
    void testListPromotionsByProduct() throws Exception {
        when(promotionService.getPromotionsByProduct(1L)).thenReturn(Arrays.asList(new Promotion()));

        mockMvc.perform(get("/api/promotions/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetPromotionById() throws Exception {
        Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setPromotionalPrice(new BigDecimal("19.90"));
        when(promotionService.getPromotionById(1L)).thenReturn(promotion);

        mockMvc.perform(get("/api/promotions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionalPrice").value(19.90));
    }

    @Test
    void testGetPromotionByIdNotFound() throws Exception {
        when(promotionService.getPromotionById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/promotions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePromotion() throws Exception {
        when(promotionService.createPromotion(any(PromotionDTO.class))).thenReturn(new Promotion());

        mockMvc.perform(post("/api/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":1,\"promotionalPrice\":19.90}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePromotionProductNotFound() throws Exception {
        when(promotionService.createPromotion(any(PromotionDTO.class))).thenThrow(new Exception("not found"));

        mockMvc.perform(post("/api/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":99,\"promotionalPrice\":19.90}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdatePromotion() throws Exception {
        Promotion promotion = new Promotion();
        promotion.setId(1L);
        promotion.setPromotionalPrice(new BigDecimal("15.00"));
        when(promotionService.updatePromotion(eq(1L), any(PromotionDTO.class))).thenReturn(promotion);

        mockMvc.perform(put("/api/promotions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":1,\"promotionalPrice\":15.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionalPrice").value(15.00));
    }

    @Test
    void testUpdatePromotionNotFound() throws Exception {
        when(promotionService.updatePromotion(eq(99L), any(PromotionDTO.class))).thenThrow(new Exception("not found"));

        mockMvc.perform(put("/api/promotions/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":1,\"promotionalPrice\":15.00}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePromotion() throws Exception {
        mockMvc.perform(delete("/api/promotions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePromotionNotFound() throws Exception {
        doThrow(new Exception("not found")).when(promotionService).deletePromotion(99L);

        mockMvc.perform(delete("/api/promotions/99"))
                .andExpect(status().isNotFound());
    }
}
