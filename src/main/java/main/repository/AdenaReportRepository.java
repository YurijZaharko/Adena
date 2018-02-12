package main.repository;

import main.entity.AdenaReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

public interface AdenaReportRepository extends JpaRepository<AdenaReport, Long>{

    @Query(value = "SELECT DISTINCT report FROM AdenaReport report LEFT JOIN FETCH report.characterL2 LEFT JOIN FETCH report.productAndPriceHolders holder " +
            "LEFT JOIN FETCH holder.productL2")
    List<AdenaReport> findAllFetchAll();

    @Query(value = "SELECT DISTINCT reports FROM AdenaReport reports LEFT JOIN FETCH reports.characterL2 characterL2 " +
            "LEFT JOIN FETCH reports.productAndPriceHolders WHERE reports.calendar > :threeMonthAgo")
    List<AdenaReport> findReportForChart(@Param(value = "threeMonthAgo") Calendar threeMonthAgo);
}
