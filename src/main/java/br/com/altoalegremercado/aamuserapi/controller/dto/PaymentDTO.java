package br.com.altoalegremercado.aamuserapi.controller.dto;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PaymentDTO {

    @Positive
    private BigDecimal amount;

    private String paymentMethod;
    private String description;
    private Long userId;
    private Long storeId;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }
}
