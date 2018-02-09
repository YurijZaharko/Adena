package main.dto;

import main.entity.CharacterL2;
import main.entity.ProductAndPriceHolder;
import main.entity.ProductL2;
import org.junit.Before;
import org.junit.Test;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.lang.Factory;
import org.meanbean.test.*;

import java.util.Calendar;
import java.util.Collections;

public class AdenaReportFormTest {
    private Configuration configuration;
    private BeanTester beanTester;

    @Before
    public void setUp() throws Exception {
        this.configuration = new ConfigurationBuilder()
                .iterations(5)
                .overrideFactory("calendar", new CalendarFactory())
                .build();

        beanTester = new BeanTester();
    }

    @Test
    public void gettersAndSettersTestCorrectness() throws Exception {
        beanTester.testBean(AdenaReportForm.class, configuration);
    }

    @Test
    public void equalsCorrectness() throws Exception {
        EqualsMethodTester tester = new EqualsMethodTester();
        tester.testEqualsMethod(new AdenaReportFormFactory(), configuration,
                "id", "productAndPriceHolders", "productL2s");
    }

    @Test
    public void hashcodeCorrectness() throws Exception {
        HashCodeMethodTester tester = new HashCodeMethodTester();
        tester.testHashCodeMethod(new AdenaReportFormFactory());
    }

    private class CalendarFactory implements Factory<Calendar> {
        @Override
        public Calendar create() {
            return Calendar.getInstance();
        }
    }

    private class AdenaReportFormFactory implements EquivalentFactory<AdenaReportForm> {
        @Override
        public AdenaReportForm create() {
            AdenaReportForm adenaReportForm = new AdenaReportForm();

            CharacterL2 characterL2 = new CharacterL2();
            characterL2.setName("testName");
            adenaReportForm.setCharacterL2(characterL2);

            ProductAndPriceHolder productAndPriceHolder = new ProductAndPriceHolder();
            productAndPriceHolder.setProductPrice(222L);
            adenaReportForm.setProductAndPriceHolders(Collections.singletonList(productAndPriceHolder));

            Long testAdenaQuantity = 15000L;
            adenaReportForm.setAdenaQuantity(testAdenaQuantity);

            Calendar testDate = Calendar.getInstance();
            testDate.set(2000, Calendar.JANUARY, 1);
            adenaReportForm.setCalendar(testDate);

            ProductL2 productL2 = new ProductL2();
            productL2.setProductName("testName");
            adenaReportForm.setProductL2s(Collections.singleton(productL2));

            return adenaReportForm;
        }
    }
}