package br.com.altoalegremercado.aamuserapi.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public byte[] generateReport(String reportName, Map<String, Object> parameters) throws Exception {
        throw new UnsupportedOperationException("Report generation not yet implemented. Requires JasperReports configuration.");
    }

    @Override
    public byte[] generateSalesReport(Long storeId) throws Exception {
        throw new UnsupportedOperationException("Sales report generation not yet implemented.");
    }

    @Override
    public byte[] generatePaymentReport(Long storeId) throws Exception {
        throw new UnsupportedOperationException("Payment report generation not yet implemented.");
    }
}
