package main.service;

import main.dto.AdenaReportForm;
import main.entity.AdenaReport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Transactional
public interface AdenaReportService {

    @Transactional(readOnly = true)
    List<AdenaReport> findAll();

    @Transactional(readOnly = true)
    List<AdenaReport> findAllFetchAll();

    AdenaReportForm addProduct(AdenaReportForm adenaReportForm);

    void saveReport(AdenaReportForm adenaReportForm);

    void delete(Long id);

    void save(AdenaReport adenaReport);

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    List<AdenaReport> findReportForChart(Calendar threeMonthAgo);
}
