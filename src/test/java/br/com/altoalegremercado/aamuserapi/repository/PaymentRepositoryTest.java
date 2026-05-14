package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Payment;
import br.com.altoalegremercado.aamuserapi.domain.model.PaymentStatus;
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
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void testSaveAndFindById() {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("150.00"));
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById(payment.getId()).orElse(null);
        assertNotNull(found);
        assertEquals(0, new BigDecimal("150.00").compareTo(found.getAmount()));
    }

    @Test
    void testFindByStoreId() {
        Store store = new Store();
        store.setName("Loja");
        store.setActive(true);
        storeRepository.save(store);

        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("50.00"));
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setStore(store);
        paymentRepository.save(payment);

        List<Payment> payments = paymentRepository.findByStoreId(store.getId());
        assertFalse(payments.isEmpty());
        assertEquals(PaymentStatus.APPROVED, payments.get(0).getStatus());
    }

    @Test
    void testFindByStatus() {
        Payment approved = new Payment();
        approved.setAmount(new BigDecimal("100.00"));
        approved.setStatus(PaymentStatus.APPROVED);
        paymentRepository.save(approved);

        Payment pending = new Payment();
        pending.setAmount(new BigDecimal("200.00"));
        pending.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(pending);

        List<Payment> approvedList = paymentRepository.findByStatus(PaymentStatus.APPROVED);
        assertFalse(approvedList.isEmpty());
        assertEquals(PaymentStatus.APPROVED, approvedList.get(0).getStatus());
    }

    @Test
    void testFindByStoreIdAndStatus() {
        Store store = new Store();
        store.setName("Loja");
        store.setActive(true);
        storeRepository.save(store);

        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("75.00"));
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setStore(store);
        paymentRepository.save(payment);

        List<Payment> result = paymentRepository.findByStoreIdAndStatus(store.getId(), PaymentStatus.REFUNDED);
        assertFalse(result.isEmpty());
        assertEquals(0, new BigDecimal("75.00").compareTo(result.get(0).getAmount()));
    }
}
