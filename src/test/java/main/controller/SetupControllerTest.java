package main.controller;

import main.entity.CharacterL2;
import main.entity.ProductL2;
import main.entity.constants.ServerNames;
import main.service.CharacterL2Service;
import main.service.ProductL2Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SetupControllerTest {

    private MockMvc mockMvc;
    private ServerNames[] ServerNamesValuesExpected;

    @Mock
    private CharacterL2Service characterL2Service;

    @Mock
    private ProductL2Service productL2Service;

    @InjectMocks
    private SetupController unit;

    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/**/");
        resolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(unit)
                .setViewResolvers(resolver)
                .build();

        ServerNamesValuesExpected = ServerNames.values();
    }

    @Test
    public void showCharacters() throws Exception {
        //given
        CharacterL2 characterL2 = new CharacterL2();
        characterL2.setName("testName");
        List<CharacterL2> l2ListExpected = Collections.singletonList(characterL2);

        Mockito.when(characterL2Service.findAll()).thenReturn(l2ListExpected);
        //when
        mockMvc.perform(get("/main/createCharacter"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("characterL2List", l2ListExpected))
                .andExpect(model().attribute("serverList", ServerNamesValuesExpected))
                .andExpect(view().name("character"));
        //then
        verify(characterL2Service, times(1)).findAll();
    }

    @Test
    public void saveCharacter() throws Exception {
        //given
        CharacterL2 expected = new CharacterL2();
        expected.setName("testName");

        ArgumentCaptor<CharacterL2> captor = ArgumentCaptor.forClass(CharacterL2.class);
        //when
        mockMvc.perform(post("/main/createCharacter").flashAttr("characterL2", expected))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/main/createCharacter"));
        //then
        verify(characterL2Service, times(1)).save(captor.capture());
        CharacterL2 actual = captor.getValue();
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void editCharacter() throws Exception {
        //given
        Long idExpected = 354L;
        CharacterL2 characterL2 = new CharacterL2();
        characterL2.setId(idExpected);

        when(characterL2Service.findOne(any(Long.class))).thenReturn(characterL2);

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        //when
        mockMvc.perform(get("/main/createCharacter/edit/{id}", idExpected))
                .andExpect(status().isOk())
                .andExpect(model().attribute("characterL2", characterL2))
                .andExpect(model().attribute("serverList", ServerNamesValuesExpected))
                .andExpect(view().name("character"));

        //then
        verify(characterL2Service, times(1)).findOne(captor.capture());
        Long idActual = captor.getValue();
        Assert.assertNotNull(idActual);
        Assert.assertEquals(idExpected, idActual);
    }

    @Test
    public void deleteCharacter() throws Exception {
        //given
        Long idExpected = 222L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        mockMvc.perform(get("/main/createCharacter/delete/{id}", idExpected))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/main/createCharacter"));
        //then
        verify(characterL2Service, times(1)).delete(captor.capture());
        Long idActual = captor.getValue();
        Assert.assertNotNull(idActual);
        Assert.assertEquals(idExpected, idActual);
    }

    @Test
    public void showProductL2() throws Exception {
        //given
        ProductL2 productL2 = new ProductL2();
        productL2.setProductName("testName");
        List<ProductL2> productL2ListExpected = Collections.singletonList(productL2);
        when(productL2Service.findAll()).thenReturn(productL2ListExpected);
        //when
        mockMvc.perform(get("/main/createProduct"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("productL2List", productL2ListExpected))
                .andExpect(view().name("product"));
        //then
        verify(productL2Service, times(1)).findAll();
    }

    @Test
    public void saveProductL2() throws Exception {
        //given
        ProductL2 productL2Expected = new ProductL2();
        productL2Expected.setProductName("myTestName");
        ArgumentCaptor<ProductL2> captor = ArgumentCaptor.forClass(ProductL2.class);
        //when
        mockMvc.perform(post("/main/createProduct").flashAttr("productL2", productL2Expected))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/main/createProduct"));
        //then
        verify(productL2Service, times(1)).save(captor.capture());
        ProductL2 productL2Actual = captor.getValue();
        Assert.assertNotNull(productL2Actual);
        Assert.assertEquals(productL2Expected, productL2Actual);
    }

    @Test
    public void editProductL2() throws Exception {
        //given
        Long id = 111L;
        ProductL2 productL2Expected = new ProductL2();
        productL2Expected.setProductName("someTestName");

        when(productL2Service.findOne(id)).thenReturn(productL2Expected);
        //when
        mockMvc.perform(get("/main/createProduct/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(model().attribute("productL2", productL2Expected))
                .andExpect(view().name("product"));
        //then
        verify(productL2Service, times(1)).findOne(id);
    }

    @Test
    public void deleteProductL2() throws Exception {
        //given
        Long idExpected = 111L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        mockMvc.perform(get("/main/createProduct/delete/{id}", idExpected))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/main/createProduct"));
        //then
        verify(productL2Service, times(1)).delete(captor.capture());
        Long idActual = captor.getValue();
        Assert.assertNotNull(idActual);
        Assert.assertEquals(idExpected, idActual);
    }
}