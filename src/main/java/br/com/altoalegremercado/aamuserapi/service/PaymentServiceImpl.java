package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.PaymentDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Payment;
import br.com.altoalegremercado.aamuserapi.domain.model.PaymentStatus;
import br.com.altoalegremercado.aamuserapi.domain.model.Store;
import br.com.altoalegremercado.aamuserapi.domain.model.User;
import br.com.altoalegremercado.aamuserapi.repository.PaymentRepository;
import br.com.altoalegremercado.aamuserapi.repository.StoreRepository;
import br.com.altoalegremercado.aamuserapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              UserRepository userRepository,
                              StoreRepository storeRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public Payment processPayment(PaymentDTO dto) throws Exception {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new Exception("User with ID (" + dto.getUserId() + ") not found!"));

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new Exception("Store with ID (" + dto.getStoreId() + ") not found!"));

        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setDescription(dto.getDescription());
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setUser(user);
        payment.setStore(store);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment cancelPayment(Long id) throws Exception {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception("Payment with ID (" + id + ") not found!"));

        if (payment.getStatus() == PaymentStatus.REFUNDED || payment.getStatus() == PaymentStatus.CANCELLED) {
            throw new Exception("Payment already " + payment.getStatus().name().toLowerCase());
        }

        payment.setStatus(PaymentStatus.CANCELLED);
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getPaymentsByStore(Long storeId) {
        return paymentRepository.findByStoreId(storeId);
    }

    @Override
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
}
