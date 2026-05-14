package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.controller.dto.PaymentDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Payment;
import br.com.altoalegremercado.aamuserapi.domain.model.PaymentStatus;

import java.util.List;

public interface PaymentService {

    List<Payment> getAllPayments();

    Payment getPaymentById(Long id);

    Payment processPayment(PaymentDTO paymentDTO) throws Exception;

    Payment cancelPayment(Long id) throws Exception;

    List<Payment> getPaymentsByStore(Long storeId);

    List<Payment> getPaymentsByStatus(PaymentStatus status);
}
