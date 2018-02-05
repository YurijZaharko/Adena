package main.controller;

import main.dto.ChartDTO;
import main.service.implamentation.ChartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class ChartsControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ChartServiceImpl chartService;

    @InjectMocks
    private ChartsController unit;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/**/");
        resolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders
                .standaloneSetup(unit)
                .setViewResolvers(resolver)
                .build();
    }

    @Test
    public void showCharts() throws Exception {
        //given
        ChartDTO chartDTOExpected = new ChartDTO();
        chartDTOExpected.setCharacterNames(Collections.singletonList("TestName"));

        Mockito.when(chartService.prepareDataForChart()).thenReturn(chartDTOExpected);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/main/charts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("chartData", chartDTOExpected));
        //then

        Mockito.verify(chartService, Mockito.times(1)).prepareDataForChart();
    }
}