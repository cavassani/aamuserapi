package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.PromotionDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Promotion;

import java.util.List;

public interface PromotionService {

    List<Promotion> getPromotionsByProduct(Long productId);

    Promotion getPromotionById(Long id);

    Promotion createPromotion(PromotionDTO dto) throws Exception;

    Promotion updatePromotion(Long id, PromotionDTO dto) throws Exception;

    void deletePromotion(Long id) throws Exception;
}
