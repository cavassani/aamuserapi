package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/sales/{storeId}")
    public ResponseEntity<byte[]> generateSalesReport(@PathVariable Long storeId) {
        try {
            byte[] report = reportService.generateSalesReport(storeId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sales_report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(report);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/payments/{storeId}")
    public ResponseEntity<byte[]> generatePaymentReport(@PathVariable Long storeId) {
        try {
            byte[] report = reportService.generatePaymentReport(storeId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=payment_report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(report);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
