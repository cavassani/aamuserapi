package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.controller.dto.PromotionDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Promotion;
import br.com.altoalegremercado.aamuserapi.service.PromotionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Promotion>> listPromotionsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(promotionService.getPromotionsByProduct(productId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        Promotion promotion = promotionService.getPromotionById(id);
        if (promotion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(promotion);
    }

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody @Valid PromotionDTO dto) {
        try {
            Promotion promotion = promotionService.createPromotion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, @RequestBody @Valid PromotionDTO dto) {
        try {
            Promotion promotion = promotionService.updatePromotion(id, dto);
            return ResponseEntity.ok(promotion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        try {
            promotionService.deletePromotion(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
