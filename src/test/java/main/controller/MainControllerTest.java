package main.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MainController unit;


    @Before
    public void setUp() throws Exception {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/**/");
        resolver.setSuffix(".jsp");

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(this.unit)
                .setViewResolvers(resolver)
                .build();
    }

    @Test
    public void main() throws Exception {
        //given
        //when
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk());
        //then
    }

    @Test
    public void login() throws Exception {
        //given
        //when
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
        //then
    }
}