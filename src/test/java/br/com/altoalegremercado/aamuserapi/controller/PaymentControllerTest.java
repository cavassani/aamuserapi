package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.controller.dto.PaymentDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.Payment;
import br.com.altoalegremercado.aamuserapi.domain.model.PaymentStatus;
import br.com.altoalegremercado.aamuserapi.service.PaymentService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void testListPayments() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(Arrays.asList(new Payment(), new Payment()));

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetPaymentById() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(new BigDecimal("100.00"));
        payment.setStatus(PaymentStatus.APPROVED);
        when(paymentService.getPaymentById(1L)).thenReturn(payment);

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    void testGetPaymentByIdNotFound() throws Exception {
        when(paymentService.getPaymentById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/payments/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testProcessPayment() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.APPROVED);
        when(paymentService.processPayment(any(PaymentDTO.class))).thenReturn(payment);

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":100.00,\"userId\":1,\"storeId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    void testProcessPaymentError() throws Exception {
        when(paymentService.processPayment(any(PaymentDTO.class)))
                .thenThrow(new Exception("User not found"));

        mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":100.00,\"userId\":99,\"storeId\":1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCancelPayment() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setStatus(PaymentStatus.CANCELLED);
        when(paymentService.cancelPayment(1L)).thenReturn(payment);

        mockMvc.perform(put("/api/payments/1/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void testCancelPaymentError() throws Exception {
        when(paymentService.cancelPayment(99L)).thenThrow(new Exception("Payment not found"));

        mockMvc.perform(put("/api/payments/99/cancel"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPaymentsByStore() throws Exception {
        when(paymentService.getPaymentsByStore(1L)).thenReturn(Arrays.asList(new Payment()));

        mockMvc.perform(get("/api/payments/store/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetPaymentsByStatus() throws Exception {
        when(paymentService.getPaymentsByStatus(PaymentStatus.APPROVED))
                .thenReturn(Arrays.asList(new Payment()));

        mockMvc.perform(get("/api/payments/status/APPROVED"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
