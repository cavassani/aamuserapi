package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    void testGenerateSalesReport() throws Exception {
        byte[] pdfContent = "PDF content".getBytes();
        when(reportService.generateSalesReport(1L)).thenReturn(pdfContent);

        mockMvc.perform(post("/api/reports/sales/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", "attachment; filename=sales_report.pdf"));
    }

    @Test
    void testGenerateSalesReportError() throws Exception {
        when(reportService.generateSalesReport(99L)).thenThrow(new Exception("Store not found"));

        mockMvc.perform(post("/api/reports/sales/99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGeneratePaymentReport() throws Exception {
        byte[] pdfContent = "PDF content".getBytes();
        when(reportService.generatePaymentReport(1L)).thenReturn(pdfContent);

        mockMvc.perform(post("/api/reports/payments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", "attachment; filename=payment_report.pdf"));
    }

    @Test
    void testGeneratePaymentReportError() throws Exception {
        when(reportService.generatePaymentReport(99L)).thenThrow(new Exception("Store not found"));

        mockMvc.perform(post("/api/reports/payments/99"))
                .andExpect(status().isNoContent());
    }
}
