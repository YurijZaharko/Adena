package main.entity;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.lang.Factory;
import org.meanbean.test.*;

public class ProductAndPriceHolderTest {

    private BeanTester beanTester;
    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        this.beanTester = new BeanTester();

        this.configuration = new ConfigurationBuilder()
                .overrideFactory("productL2", new ProductL2Factory())
                .overrideFactory("adenaReport", new AdenaReportFactory())
                .iterations(5)
                .build();
    }

    @Test
    public void gettersAndSettersCorrectness() throws Exception {
        beanTester.testBean(ProductAndPriceHolder.class, configuration);
    }

    @Test
    public void equalsCorrectness() throws Exception {
        EqualsMethodTester tester = new EqualsMethodTester();
        tester.testEqualsMethod(new ProductAndPriceHolderEquivalentFactory(), configuration, "id");
    }

    @Test
    public void hashCodeCorrectness() throws Exception {
        HashCodeMethodTester tester = new HashCodeMethodTester();
        tester.testHashCodeMethod(new ProductAndPriceHolderEquivalentFactory());
    }

    private class ProductL2Factory implements Factory<ProductL2>{

        @Override
        public ProductL2 create() {
            ProductL2 productL2 = new ProductL2();
            productL2.setProductName("testName");
            return productL2;
        }
    }

    private class AdenaReportFactory implements Factory<AdenaReport>{

        @Override
        public AdenaReport create() {
            AdenaReport adenaReport = new AdenaReport();
            adenaReport.setAdenaQuantity(3500L);
            return adenaReport;
        }
    }

    private class ProductAndPriceHolderEquivalentFactory implements EquivalentFactory<ProductAndPriceHolder>{

        @Override
        public ProductAndPriceHolder create() {
            ProductAndPriceHolder productAndPriceHolder = new ProductAndPriceHolder();
            productAndPriceHolder.setId(400L);
            productAndPriceHolder.setProductPrice(3500L);
            productAndPriceHolder.setProductQuantity(15L);

            ProductL2Factory productL2Factory = new ProductL2Factory();
            productAndPriceHolder.setProductL2(productL2Factory.create());

            AdenaReportFactory adenaReportFactory = new AdenaReportFactory();
            productAndPriceHolder.setAdenaReport(adenaReportFactory.create());

            return productAndPriceHolder;
        }
    }

}