package br.com.altoalegremercado.aamuserapi.service;

import java.util.Map;

public interface ReportService {

    byte[] generateReport(String reportName, Map<String, Object> parameters) throws Exception;

    byte[] generateSalesReport(Long storeId) throws Exception;

    byte[] generatePaymentReport(Long storeId) throws Exception;
}
