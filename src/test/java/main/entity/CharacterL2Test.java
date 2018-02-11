package main.entity;

import main.entity.constants.ServerNames;
import org.junit.Before;
import org.junit.Test;
import org.meanbean.lang.EquivalentFactory;
import org.meanbean.lang.Factory;
import org.meanbean.test.*;

import java.util.Collections;
import java.util.Set;

public class CharacterL2Test {

    private BeanTester beanTester;
    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        this.beanTester = new BeanTester();

        this.configuration = new ConfigurationBuilder()
                .iterations(5)
                .overrideFactory("adenaReports", new AdenaReportsFactory())
                .build();
    }

    @Test
    public void gettersAndSettersCorrectness() throws Exception {
        beanTester.testBean(CharacterL2.class, configuration);
    }

    @Test
    public void equalsCorrectness() throws Exception {
        EqualsMethodTester tester = new EqualsMethodTester();
        tester.testEqualsMethod(new CharacterL2EquivalentFactory(), configuration, "id", "adenaReports");
    }

    @Test
    public void hashCodeCorrectness() throws Exception {
        HashCodeMethodTester tester = new HashCodeMethodTester();
        tester.testHashCodeMethod(new CharacterL2EquivalentFactory());
    }

    private class AdenaReportsFactory implements Factory<Set<AdenaReport>>{

        @Override
        public Set<AdenaReport> create() {
            AdenaReport adenaReport = new AdenaReport();
            adenaReport.setAdenaQuantity(5000L);
            return Collections.singleton(adenaReport);
        }
    }

    private class CharacterL2EquivalentFactory implements EquivalentFactory<CharacterL2>{

        @Override
        public CharacterL2 create() {
            CharacterL2 characterL2 = new CharacterL2();
            characterL2.setId(500L);
            characterL2.setServerNames(ServerNames.AIRIN);
            characterL2.setName("testName");

            AdenaReportsFactory adenaReportsFactory = new AdenaReportsFactory();
            characterL2.setAdenaReports(adenaReportsFactory.create());
            return characterL2;
        }
    }
}