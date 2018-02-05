package main.controller;

import main.dto.AdenaReportForm;
import main.entity.AdenaReport;
import main.entity.CharacterL2;
import main.entity.ProductAndPriceHolder;
import main.entity.ProductL2;
import main.service.AdenaReportService;
import main.service.CharacterL2Service;
import main.service.ProductL2Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AdenaReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CharacterL2Service characterL2Service;

    @Mock
    private ProductL2Service productL2Service;

    @Mock
    private AdenaReportService adenaReportService;

    @InjectMocks
    private AdenaReportController unit;

    private CharacterL2 characterL2Expected;
    private ProductL2 productL2Expected;
    private AdenaReport adenaReportExpected;

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

        this.characterL2Expected = new CharacterL2();
        characterL2Expected.setName("testName");

        this.productL2Expected = new ProductL2();
        productL2Expected.setProductName("testProduct");

        this.adenaReportExpected = new AdenaReport();
        int testValueAdenaQuantity = 100;
        adenaReportExpected.setAdenaQuantity(testValueAdenaQuantity);

    }

    @Test
    public void showReport() throws Exception {
        //given
        Mockito.when(characterL2Service.findAll()).thenReturn(Collections.singletonList(characterL2Expected));
        Mockito.when(productL2Service.findAll()).thenReturn(Collections.singletonList(productL2Expected));
        Mockito.when(adenaReportService.findAllFetchAll()).thenReturn(Collections.singletonList(adenaReportExpected));

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/main"))
                .andExpect(view().name("report"))
                .andExpect(model().attributeExists("adenaReport",
                        "adenaReportForm", "characterList", "productL2List", "adenaReportList"))
                .andExpect(model().attribute("characterList", Collections.singletonList(characterL2Expected)))
                .andExpect(model().attribute("productL2List", Collections.singletonList(productL2Expected)))
                .andExpect(model().attribute("adenaReportList", Collections.singletonList(adenaReportExpected)))
                .andExpect(status().isOk());
        //then
        Mockito.verify(characterL2Service, times(1)).findAll();
        Mockito.verify(productL2Service, times(1)).findAll();
        Mockito.verify(adenaReportService, times(1)).findAllFetchAll();
    }


    @Test
    public void addProduct() throws Exception {
        //given
        ProductAndPriceHolder productAndPriceHolder = new ProductAndPriceHolder();
        productAndPriceHolder.setProductPrice(100L);
        productAndPriceHolder.setProductQuantity(10L);
        AdenaReportForm adenaReportFormExpected = new AdenaReportForm();
        adenaReportFormExpected.setProductAndPriceHolders(Collections.singletonList(productAndPriceHolder));

        Mockito.when(characterL2Service.findAll()).thenReturn(Collections.singletonList(characterL2Expected));
        Mockito.when(productL2Service.findAll()).thenReturn(Collections.singletonList(productL2Expected));
        Mockito.when(adenaReportService.findAllFetchAll()).thenReturn(Collections.singletonList(adenaReportExpected));
        Mockito.when(adenaReportService.addProduct(any(AdenaReportForm.class))).thenReturn(adenaReportFormExpected);

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/main/createAdenaReport")
                .param("addProduct", "addProduct")
                .flashAttr("adenaReportForm", adenaReportFormExpected))
                .andExpect(status().isOk())
                .andExpect(view().name("report"))
                .andExpect(model().attribute("characterList", Collections.singletonList(characterL2Expected)))
                .andExpect(model().attribute("productL2List", Collections.singletonList(productL2Expected)))
                .andExpect(model().attribute("adenaReportList", Collections.singletonList(adenaReportExpected)))
                .andExpect(model().attribute("adenaReportForm", adenaReportFormExpected));
        //then
        Mockito.verify(characterL2Service, times(1)).findAll();
        Mockito.verify(productL2Service, times(1)).findAll();
        Mockito.verify(adenaReportService, times(1)).findAllFetchAll();
        Mockito.verify(adenaReportService, times(1)).addProduct(any(AdenaReportForm.class));
    }

    @Test
    public void saveReport() throws Exception {
        //given
        AdenaReportForm adenaReportFormExpected = new AdenaReportForm();
        adenaReportFormExpected.setAdenaQuantity(1000L);
        ArgumentCaptor<AdenaReportForm> captor = ArgumentCaptor.forClass(AdenaReportForm.class);
        //when
        mockMvc.perform(MockMvcRequestBuilders
                .post("/main/createAdenaReport")
                .param("saveReport", "saveReport")
                .flashAttr("adenaReportForm", adenaReportFormExpected))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/main"));
        //then
        verify(adenaReportService, times(1)).saveReport(captor.capture());
        AdenaReportForm adenaReportFormActual = captor.getValue();
        Assert.assertEquals(adenaReportFormExpected, adenaReportFormActual);
    }


    @Test
    public void deleteReport() throws Exception {
        //given
        Long expected = 555L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/main/report/delete/{id}", expected))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/main"));
        //then
        verify(adenaReportService, times(1)).delete(captor.capture());
        Long actual = captor.getValue();
        Assert.assertEquals(expected, actual);
    }
}