package main.entity;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.lang.Factory;
import org.meanbean.test.*;

import java.util.Collections;
import java.util.Set;

public class ProductL2Test {

    private BeanTester beanTester;
    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        this.beanTester = new BeanTester();

        this.configuration = new ConfigurationBuilder()
                .iterations(5)
                .overrideFactory("productAndPriceHolders", new ProductAndPriceHolderFactory())
                .build();
    }

    @Test
    public void gettersAndSettersCorrectness() throws Exception {
        beanTester.testBean(ProductL2.class, configuration);
    }

    @Test
    public void equalsCorrectness() throws Exception {
        EqualsMethodTester tester = new EqualsMethodTester();
        tester.testEqualsMethod(ProductL2.class, this.configuration, "id", "productAndPriceHolders");
    }

    @Test
    public void hashCodeCorrectness() throws Exception {
        HashCodeMethodTester tester = new HashCodeMethodTester();
        tester.testHashCodeMethod(ProductL2.class);
    }

    private class ProductAndPriceHolderFactory implements Factory<Set<ProductAndPriceHolder>> {
        @Override
        public Set<ProductAndPriceHolder> create() {
            ProductAndPriceHolder productAndPriceHolder = new ProductAndPriceHolder();
            productAndPriceHolder.setProductPrice(8500L);
            return Collections.singleton(productAndPriceHolder);
        }
    }
}