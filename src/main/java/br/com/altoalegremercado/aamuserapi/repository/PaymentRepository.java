package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Payment;
import br.com.altoalegremercado.aamuserapi.domain.model.PaymentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    List<Payment> findByStoreId(Long storeId);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByStoreIdAndStatus(Long storeId, PaymentStatus status);
}
