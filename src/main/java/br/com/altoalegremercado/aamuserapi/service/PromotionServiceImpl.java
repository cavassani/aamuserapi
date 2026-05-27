package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.PromotionDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Product;
import br.com.altoalegremercado.aamuserapi.domain.model.Promotion;
import br.com.altoalegremercado.aamuserapi.repository.ProductRepository;
import br.com.altoalegremercado.aamuserapi.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository,
                                ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Promotion> getPromotionsByProduct(Long productId) {
        return promotionRepository.findByProductId(productId);
    }

    @Override
    public Promotion getPromotionById(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }

    @Override
    public Promotion createPromotion(PromotionDTO dto) throws Exception {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new Exception("Product with ID (" + dto.getProductId() + ") not found!"));

        Promotion promotion = new Promotion();
        promotion.setProduct(product);
        promotion.setPromotionalPrice(dto.getPromotionalPrice());
        promotion.setDescription(dto.getDescription());
        promotion.setActive(dto.getActive() != null ? dto.getActive() : true);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (dto.getStartDate() != null) {
            promotion.setStartDate(sdf.parse(dto.getStartDate()));
        }
        if (dto.getEndDate() != null) {
            promotion.setEndDate(sdf.parse(dto.getEndDate()));
        }

        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(Long id, PromotionDTO dto) throws Exception {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new Exception("Promotion with ID (" + id + ") not found!"));

        promotion.setPromotionalPrice(dto.getPromotionalPrice());
        promotion.setDescription(dto.getDescription());
        promotion.setActive(dto.getActive());

        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new Exception("Product with ID (" + dto.getProductId() + ") not found!"));
            promotion.setProduct(product);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if (dto.getStartDate() != null) {
            promotion.setStartDate(sdf.parse(dto.getStartDate()));
        }
        if (dto.getEndDate() != null) {
            promotion.setEndDate(sdf.parse(dto.getEndDate()));
        }

        return promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(Long id) throws Exception {
        if (promotionRepository.findById(id).isEmpty()) {
            throw new Exception("Promotion with ID (" + id + ") not found!");
        }
        promotionRepository.deleteById(id);
    }
}
