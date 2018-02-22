package main.service.implamentation;

import main.dto.ChartDTO;
import main.entity.AdenaReport;
import main.entity.CharacterL2;
import main.service.AdenaReportService;
import main.service.CharacterL2Service;
import main.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChartServiceImpl implements ChartService {

    private CharacterL2Service characterL2Service;
    private AdenaReportService adenaReportService;
    private static final int WEEK_DEPTH = 6;

    public ChartDTO prepareDataForChart() {
        ChartDTO chartDTO = new ChartDTO();
        Map<Long, List<Long>> dataMap = new LinkedHashMap<>();

        List<String> names = getAllNames();
        chartDTO.setCharacterNames(names);

        Calendar now = Calendar.getInstance();

        List<AdenaReport> adenaReportList = adenaReportService.findReportForChart(createPastCalendarTime());

        for (int i = 1; i < WEEK_DEPTH; i++) {
            Calendar iteration = (Calendar) now.clone();
            iteration.add(Calendar.WEEK_OF_MONTH, -i);
            List<Long> earned = new LinkedList<>();
            for (String name : names) {
                Optional<AdenaReport> newestAdenaReport = findNewestAdenaReport(adenaReportList, name, iteration);
                Optional<AdenaReport> oldestAdenaReport = findOldestAdenaReport(adenaReportList, name, iteration);

                if (oldestAdenaReport.isPresent() && newestAdenaReport.isPresent()) {
                    long result = calculateEarnedAdena(oldestAdenaReport.get(), newestAdenaReport.get(), name, adenaReportList);
                    earned.add(result);
                } else {
                    earned.add(0L);
                }
            }
            long timeInMillis = iteration.getTimeInMillis();
            dataMap.put(timeInMillis, earned);
        }
        chartDTO.setDataMap(dataMap);
        return chartDTO;
    }

    private long calculateEarnedAdena(AdenaReport oldestAdenaReport, AdenaReport newestAdenaReport, String name, List<AdenaReport> adenaReportList) {
        long soldAdena = calculateSoldAdena(adenaReportList, name, oldestAdenaReport);
        long newestSum = calculateNewestSum(newestAdenaReport);
        long olderSum = calculateOlderSum(oldestAdenaReport);

        removeAlreadyCalculatedAdenaReports(adenaReportList, name, oldestAdenaReport);

        return newestSum - olderSum + soldAdena;
    }

    private void removeAlreadyCalculatedAdenaReports(List<AdenaReport> adenaReportList, String name, AdenaReport oldestAdenaReport) {
        List<AdenaReport> removeList = adenaReportList
                .stream()
                .sorted(Comparator.comparing(AdenaReport::getCalendar))
                .filter(report -> report.getCharacterL2().getName().equals(name))
                .filter(report -> report.getCalendar().after(oldestAdenaReport.getCalendar()))
                .collect(Collectors.toList());
        adenaReportList.removeAll(removeList);
    }

    private long calculateOlderSum(AdenaReport adenaReportOld) {
        return adenaReportOld.getAdenaQuantity() +
                Optional.ofNullable(adenaReportOld.getProductAndPriceHolders()).orElseGet(Collections::emptySet)
                        .stream()
                        .mapToLong(value -> value.getProductQuantity() * value.getProductPrice())
                        .sum();
    }

    private long calculateNewestSum(AdenaReport adenaReportNewest) {
        return adenaReportNewest.getAdenaQuantity() +
                Optional.ofNullable(adenaReportNewest.getProductAndPriceHolders()).orElseGet(Collections::emptySet)
                        .stream()
                        .mapToLong(value -> value.getProductQuantity() * value.getProductPrice())
                        .sum();
    }

    private long calculateSoldAdena(List<AdenaReport> adenaReportList, String name, AdenaReport oldestAdenaReport) {
        return adenaReportList
                .stream()
                .filter(report -> report.getCharacterL2().getName().equals(name))
                .filter(report -> report.getCalendar().after(oldestAdenaReport.getCalendar()))
                .mapToLong(AdenaReport::getAdenaSold).sum();
    }

    private Optional<AdenaReport> findOldestAdenaReport(List<AdenaReport> adenaReportList, String name, Calendar iteration) {
        return adenaReportList
                .stream()
                .filter(report -> report.getCharacterL2().getName().equals(name))
                .sorted((Comparator.comparing(AdenaReport::getCalendar).reversed()))
                .filter(report -> report.getCalendar().before(iteration))
                .findFirst();
    }

    private Optional<AdenaReport> findNewestAdenaReport(List<AdenaReport> adenaReportList, String name, Calendar iteration) {
        return adenaReportList
                .stream()
                .filter(report -> report.getCharacterL2().getName().equals(name))
                .filter(report -> report.getCalendar().after(iteration))
                .max(Comparator.comparing(AdenaReport::getCalendar));
    }

    private Calendar createPastCalendarTime() {
        Calendar severalWeekAgo = Calendar.getInstance();
        severalWeekAgo.add(Calendar.WEEK_OF_MONTH, -WEEK_DEPTH);
        return severalWeekAgo;
    }

    private List<String> getAllNames() {
        List<CharacterL2> all = characterL2Service.findAll();
        return all.stream()
                .map(CharacterL2::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }


    @Autowired
    public void setCharacterL2Service(CharacterL2Service characterL2Service) {
        this.characterL2Service = characterL2Service;
    }

    @Autowired
    public void setAdenaReportService(AdenaReportService adenaReportService) {
        this.adenaReportService = adenaReportService;
    }
}
