package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Long> {

    List<Promotion> findByProductId(Long productId);

    List<Promotion> findByProductIdAndActive(Long productId, Boolean active);
}
