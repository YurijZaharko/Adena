package main.dto;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;

public class ChartDTOTest {
    private BeanTester beanTester;
    private Configuration configuration;


    @Before
    public void setUp() throws Exception {
        this.beanTester = new BeanTester();
        this.configuration = new ConfigurationBuilder()
                .iterations(5)
                .build();
    }

    @Test
    public void gettersAndSettersCorrectness() throws Exception {
        beanTester.testBean(ChartDTO.class, this.configuration);
    }
}