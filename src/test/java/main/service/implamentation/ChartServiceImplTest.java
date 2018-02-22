package main.service.implamentation;

import main.dto.ChartDTO;
import main.entity.AdenaReport;
import main.entity.CharacterL2;
import main.entity.ProductAndPriceHolder;
import main.service.AdenaReportService;
import main.service.CharacterL2Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class ChartServiceImplTest {
    private List<CharacterL2> characterL2List;
    private List<AdenaReport> adenaReportList;
    private List<Integer> timeInDaysExpected;
    private List<String> namesExpected;
    private List<List<Long>> dataMapExpected;

    @Mock
    private CharacterL2Service characterL2Service;

    @Mock
    private AdenaReportService adenaReportService;

    @InjectMocks
    private ChartServiceImpl unit;


    @Before
    public void setUp() throws Exception {
        String nameOne = "testNameFirst";
        String nameTwo = "testNameSecond";
        CharacterL2 characterL2First = CharacterL2.builder().name(nameOne).build();
        CharacterL2 characterL2Second = CharacterL2.builder().name(nameTwo).build();
        characterL2List = Arrays.asList(characterL2First, characterL2Second);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.WEEK_OF_YEAR, -1);

        Calendar oneWeekAgo = (Calendar) now.clone();
        oneWeekAgo.add(Calendar.WEEK_OF_YEAR, -1);

        Calendar twoWeeksAgo = (Calendar) now.clone();
        twoWeeksAgo.add(Calendar.WEEK_OF_YEAR, -2);

        Calendar threeWeeksAgo = (Calendar) now.clone();
        threeWeeksAgo.add(Calendar.WEEK_OF_YEAR, -3);

        Calendar fourWeeksAgo = (Calendar) now.clone();
        fourWeeksAgo.add(Calendar.WEEK_OF_YEAR, -4);

        AdenaReport adenaReportFirst = AdenaReport.builder().characterL2(characterL2First).calendar(now).adenaQuantity(10000).build();
        AdenaReport adenaReportSecond = AdenaReport.builder().characterL2(characterL2First).calendar(oneWeekAgo).adenaQuantity(8500).build();
        AdenaReport adenaReportThird = AdenaReport.builder().characterL2(characterL2First).calendar(twoWeeksAgo).adenaQuantity(2000).adenaSold(6000).build();
        AdenaReport adenaReportForth = AdenaReport.builder().characterL2(characterL2First).calendar(threeWeeksAgo).adenaQuantity(2000)
                .productAndPriceHolders(Collections.singleton(ProductAndPriceHolder.builder().productQuantity(2L).productPrice(1000L).build())).build();
        AdenaReport adenaReportFifth = AdenaReport.builder().characterL2(characterL2First).calendar(fourWeeksAgo).adenaQuantity(1000).build();

        AdenaReport adenaReportSixth = AdenaReport.builder().characterL2(characterL2Second).adenaQuantity(5000).calendar(now).build();
        AdenaReport adenaReportSeventh = AdenaReport.builder().characterL2(characterL2Second).adenaQuantity(5500).calendar(threeWeeksAgo).build();
        AdenaReport adenaReportEight = AdenaReport.builder().characterL2(characterL2Second).adenaQuantity(5600).calendar(fourWeeksAgo).build();

        adenaReportList = new ArrayList<>();
        adenaReportList.add(adenaReportFirst);
        adenaReportList.add(adenaReportSecond);
        adenaReportList.add(adenaReportThird);
        adenaReportList.add(adenaReportForth);
        adenaReportList.add(adenaReportFifth);
        adenaReportList.add(adenaReportSixth);
        adenaReportList.add(adenaReportSeventh);
        adenaReportList.add(adenaReportEight);

        timeInDaysExpected = new LinkedList<>();
        timeInDaysExpected.add(now.get(Calendar.WEEK_OF_YEAR));
        timeInDaysExpected.add(oneWeekAgo.get(Calendar.WEEK_OF_YEAR));
        timeInDaysExpected.add(twoWeeksAgo.get(Calendar.WEEK_OF_YEAR));
        timeInDaysExpected.add(threeWeeksAgo.get(Calendar.WEEK_OF_YEAR));
        timeInDaysExpected.add(fourWeeksAgo.get(Calendar.WEEK_OF_YEAR));

        namesExpected = Arrays.asList(nameOne, nameTwo);

        dataMapExpected = new LinkedList<>();
        dataMapExpected.add(Arrays.asList(0L, 0L));
        dataMapExpected.add(Arrays.asList(1500L, -500L));
        dataMapExpected.add(Arrays.asList(6500L, 0L));
        dataMapExpected.add(Arrays.asList(4000L, 0L));
        dataMapExpected.add(Arrays.asList(3000L, -100L));
        }

    @Test
    public void prepareDataForChart() throws Exception {
        //given
        Mockito.when(characterL2Service.findAll()).thenReturn(characterL2List);
        Mockito.when(adenaReportService.findReportForChart(Mockito.any(Calendar.class))).thenReturn(adenaReportList);
        //when
        final ChartDTO chartDTO = unit.prepareDataForChart();
        //then
        Assert.assertTrue(namesExpected.equals(chartDTO.getCharacterNames()));
        List<Long> timeInMillis = new LinkedList<>(chartDTO.getDataMap().keySet());
        final List<Integer> timeInDaysActual = timeInMillis.stream().map(aLong -> {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(aLong);
            return instance.get(Calendar.WEEK_OF_YEAR);
        }).collect(Collectors.toList());

        Assert.assertTrue(timeInDaysExpected.equals(timeInDaysActual));
        final List<List<Long>> dataMapActual = new LinkedList<>(chartDTO.getDataMap().values());
        Assert.assertTrue(dataMapExpected.equals(dataMapActual));
    }

}