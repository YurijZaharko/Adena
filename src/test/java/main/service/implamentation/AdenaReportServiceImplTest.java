package main.service.implamentation;

import main.dto.AdenaReportForm;
import main.entity.AdenaReport;
import main.entity.CharacterL2;
import main.entity.ProductAndPriceHolder;
import main.entity.ProductL2;
import main.repository.AdenaReportRepository;
import main.service.ProductAndPriceHolderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class AdenaReportServiceImplTest {
    private AdenaReport adenaReport;

    @Spy
    private AdenaReportForm adenaReportForm;

    @Captor
    private ArgumentCaptor<Set<ProductAndPriceHolder>> setArgumentCaptor;

    @Mock
    private AdenaReportRepository adenaReportRepository;

    @Mock
    private ProductAndPriceHolderService productAndPriceHolderService;

    @InjectMocks
    private AdenaReportServiceImpl unit;

    @Before
    public void setUp() throws Exception {
        ProductAndPriceHolder productAndPriceHolder = ProductAndPriceHolder.builder().productPrice(999L).productQuantity(3L).build();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1);

        this.adenaReport = AdenaReport.builder()
                .id(15L)
                .characterL2(CharacterL2.builder().name("testName").build())
                .adenaSold(1111L)
                .adenaQuantity(8888L)
                .calendar(calendar)
                .productAndPriceHolders(Collections.singleton(productAndPriceHolder))
                .build();

        this.adenaReportForm = new AdenaReportForm();
        adenaReportForm.setId(adenaReport.getId());
        adenaReportForm.setProductAndPriceHolders(new ArrayList<>(adenaReport.getProductAndPriceHolders()));
        adenaReportForm.setAdenaQuantity(adenaReport.getAdenaQuantity());
        adenaReportForm.setAdenaSold(adenaReport.getAdenaSold());
        adenaReportForm.setCalendar(adenaReport.getCalendar());
        adenaReportForm.setCharacterL2(adenaReport.getCharacterL2());
    }

    @Test
    public void findAll() throws Exception {
        //given
        List<AdenaReport> adenaReportListExpected = Collections.singletonList(adenaReport);
        Mockito.when(adenaReportRepository.findAll()).thenReturn(adenaReportListExpected);
        //when
        List<AdenaReport> adenaReportsListActual = unit.findAll();
        //then
        Assert.assertNotNull(adenaReportsListActual);
        Assert.assertTrue(adenaReportListExpected.equals(adenaReportsListActual));
    }

    @Test
    public void findAllFetchAll() throws Exception {
        //given
        List<AdenaReport> adenaReportListExpected = Collections.singletonList(adenaReport);
        Mockito.when(adenaReportRepository.findAllFetchAll()).thenReturn(adenaReportListExpected);
        //when
        List<AdenaReport> adenaReportsListActual = unit.findAllFetchAll();
        //then
        Assert.assertNotNull(adenaReportsListActual);
        Assert.assertTrue(adenaReportListExpected.equals(adenaReportsListActual));
    }

    @Test
    public void addProduct_withNotNullProductList() throws Exception {
        //given
        final ProductL2 productL2First = ProductL2.builder().productName("testNameFirst").build();
        final ProductL2 productL2Second = ProductL2.builder().productName("testNameSecond").build();

        AdenaReportForm adenaReportForm = new AdenaReportForm();
        List<ProductL2> productL2ListExpected = Arrays.asList(productL2First, productL2Second);
        adenaReportForm.setProductL2s(new HashSet<>(productL2ListExpected));

        //when
        final AdenaReportForm adenaReportFormResult = unit.addProduct(adenaReportForm);
        //then
        List<ProductAndPriceHolder> productAndPriceHolders = adenaReportFormResult.getProductAndPriceHolders();
        Assert.assertNotNull(productAndPriceHolders);
        Assert.assertEquals(2, productAndPriceHolders.size());
        List<ProductL2> productL2ListActual = productAndPriceHolders
                .stream()
                .map(ProductAndPriceHolder::getProductL2)
                .sorted(Comparator.comparing(ProductL2::getProductName))
                .collect(Collectors.toList());

        Assert.assertTrue(productL2ListExpected.equals(productL2ListActual));
    }

    @Test
    public void addProduct_withNullProductList_shouldReturnFormWithEmptyProductAndPriceHolders() throws Exception {
        //given
        AdenaReportForm adenaReportForm = new AdenaReportForm();
        //when
        final AdenaReportForm adenaReportFormResult = unit.addProduct(adenaReportForm);
        //then
        final List<ProductAndPriceHolder> productAndPriceHoldersActual = adenaReportFormResult.getProductAndPriceHolders();
        Assert.assertNotNull(productAndPriceHoldersActual);
        Assert.assertTrue(productAndPriceHoldersActual.isEmpty());
    }

    @Test
    public void saveReport_withNull() throws Exception {
        //given
        ArgumentCaptor<AdenaReport> reportArgumentCaptor = ArgumentCaptor.forClass(AdenaReport.class);
        //when
        unit.saveReport(adenaReportForm);
        //then
        Mockito.verify(adenaReportRepository, Mockito.times(1)).save(reportArgumentCaptor.capture());
        final AdenaReport adenaReportActual = reportArgumentCaptor.getValue();
        Assert.assertNotNull(adenaReportActual);
        Assert.assertEquals(adenaReport, adenaReportActual);
        Assert.assertEquals(adenaReport.getId(), adenaReportActual.getId());

        Assert.assertEquals(adenaReport, adenaReport);
    }

    @Test
    public void delete() throws Exception {
        //given
        Long idExpected = 355L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        unit.delete(idExpected);
        //then
        Mockito.verify(adenaReportRepository, Mockito.times(1)).delete(captor.capture());
        final Long idActual = captor.getValue();
        Assert.assertEquals(idExpected, idActual);
    }

    @Test
    public void findReportForChart() throws Exception {
        //given
        Calendar calendarExpected = Calendar.getInstance();
        calendarExpected.set(2000,Calendar.JANUARY, 1);
        ArgumentCaptor<Calendar> captor = ArgumentCaptor.forClass(Calendar.class);
        //when
        unit.findReportForChart(calendarExpected);
        //then
        Mockito.verify(adenaReportRepository, Mockito.times(1)).findReportForChart(captor.capture());
        final Calendar calendarActual = captor.getValue();
        Assert.assertEquals(calendarExpected, calendarActual);

    }
}