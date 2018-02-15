package main.service.implamentation;

import lombok.NonNull;
import main.dto.AdenaReportForm;
import main.entity.AdenaReport;
import main.entity.ProductAndPriceHolder;
import main.entity.ProductL2;
import main.repository.AdenaReportRepository;
import main.service.AdenaReportService;
import main.service.ProductAndPriceHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdenaReportServiceImpl implements AdenaReportService {

    private AdenaReportRepository adenaReportRepository;
    private ProductAndPriceHolderService productAndPriceHolderService;

    @Override
    public List<AdenaReport> findAll() {
        return adenaReportRepository.findAll();
    }

    @Override
    public List<AdenaReport> findAllFetchAll() {
        return adenaReportRepository.findAllFetchAll();
    }

    @Override
    public AdenaReportForm addProduct(@NonNull AdenaReportForm adenaReportForm) {
        Set<ProductL2> productL2s = adenaReportForm.getProductL2s();
        List<ProductAndPriceHolder> productAndPriceHolders = new LinkedList<>();
        Optional.ofNullable(productL2s)
                .orElseGet(Collections::emptySet)
                .forEach(productL2 -> productAndPriceHolders.add(ProductAndPriceHolder.builder().productL2(productL2).build()));
        adenaReportForm.setProductAndPriceHolders(productAndPriceHolders);
        return adenaReportForm;
    }

    @Override
    public void saveReport(@NonNull AdenaReportForm adenaReportForm) {
        AdenaReport adenaReport = convertForm(adenaReportForm);
        adenaReportRepository.save(adenaReport);

        List<ProductAndPriceHolder> productAndPriceHolders = adenaReportForm.getProductAndPriceHolders();
        Set<ProductAndPriceHolder> set = new HashSet<>(Optional.ofNullable(productAndPriceHolders).orElseGet(Collections::emptyList));
        set.forEach(productAndPriceHolder -> productAndPriceHolder.setAdenaReport(adenaReport));
        productAndPriceHolderService.save(set);
    }


    private AdenaReport convertForm(@NonNull AdenaReportForm adenaReportForm){
        return AdenaReport.builder()
                .id(adenaReportForm.getId())
                .characterL2(adenaReportForm.getCharacterL2())
                .adenaQuantity(adenaReportForm.getAdenaQuantity())
                .adenaSold(adenaReportForm.getAdenaSold())
                .calendar(Calendar.getInstance())
                .build();
    }

    @Override
    public void delete(@NonNull Long id) {
        adenaReportRepository.delete(id);
    }

    @Override
    public List<AdenaReport> findReportForChart(@NonNull Calendar threeMonthAgo) {
        return adenaReportRepository.findReportForChart(threeMonthAgo);
    }

    @Autowired
    public void setAdenaReportRepository(AdenaReportRepository adenaReportRepository) {
        this.adenaReportRepository = adenaReportRepository;
    }

    @Autowired
    public void setProductAndPriceHolderService(ProductAndPriceHolderService productAndPriceHolderService) {
        this.productAndPriceHolderService = productAndPriceHolderService;
    }
}
