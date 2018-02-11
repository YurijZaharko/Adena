package main.entity;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.lang.Factory;
import org.meanbean.test.BeanTester;
import org.meanbean.test.Configuration;
import org.meanbean.test.ConfigurationBuilder;
import org.meanbean.test.EqualsMethodTester;

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

public class AdenaReportTest {
    private BeanTester beanTester;
    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        this.beanTester = new BeanTester();
        this.configuration = new ConfigurationBuilder()
                .overrideFactory("characterL2", new CharacterL2Factory())
                .overrideFactory("productAndPriceHolders", new ProductAndPriceHolderFactory())
                .overrideFactory("calendar", new CalendarFactory())
                .iterations(5)
                .build();
    }

    @Test
    public void gettersAndSettersCorrectness() throws Exception {
        beanTester.testBean(AdenaReport.class, configuration);
    }

    @Test
    public void equalsCorrectness() throws Exception {
        EqualsMethodTester tester = new EqualsMethodTester();
        tester.testEqualsMethod(new AdenaReportEquivalentFactory(), configuration,  "id", "productAndPriceHolders");
    }

    private class CharacterL2Factory implements Factory<CharacterL2> {

        @Override
        public CharacterL2 create() {
            CharacterL2 characterL2 = new CharacterL2();
            characterL2.setName("testName");
            return characterL2;
        }
    }

    private class ProductAndPriceHolderFactory implements Factory<Set<ProductAndPriceHolder>> {

        @Override
        public Set<ProductAndPriceHolder> create() {
            ProductAndPriceHolder productAndPriceHolder = new ProductAndPriceHolder();
            productAndPriceHolder.setProductPrice(5000L);
            return Collections.singleton(productAndPriceHolder);
        }
    }

    private class CalendarFactory implements Factory<Calendar> {

        @Override
        public Calendar create() {
            return Calendar.getInstance();
        }
    }

    private class AdenaReportEquivalentFactory implements EquivalentFactory<AdenaReport> {

        @Override
        public AdenaReport create() {
            AdenaReport adenaReport = new AdenaReport();
            adenaReport.setId(5L);

            CharacterL2Factory characterL2Factory = new CharacterL2Factory();
            adenaReport.setCharacterL2(characterL2Factory.create());

            ProductAndPriceHolderFactory productAndPriceHolderFactory = new ProductAndPriceHolderFactory();
            adenaReport.setProductAndPriceHolders(productAndPriceHolderFactory.create());

            adenaReport.setAdenaQuantity(500L);
            adenaReport.setAdenaSold(100L);

            Calendar calendar = Calendar.getInstance();
            calendar.set(2000, Calendar.JANUARY, 1);
            adenaReport.setCalendar(calendar);

            return adenaReport;
        }
    }
}