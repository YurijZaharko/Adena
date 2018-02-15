package main.service;

import main.dto.AdenaReportForm;
import main.entity.AdenaReport;

import java.util.Calendar;
import java.util.List;

public interface AdenaReportService {
    List<AdenaReport> findAll();

    List<AdenaReport> findAllFetchAll();

    AdenaReportForm addProduct(AdenaReportForm adenaReportForm);

    void saveReport(AdenaReportForm adenaReportForm);

    void delete(Long id);

    List<AdenaReport> findReportForChart(Calendar threeMonthAgo);
}
