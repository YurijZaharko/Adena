package main.configs;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@PropertySource(value = "/properties/db.properties")
public class DbConfig {

    private final Environment environment;

    @Autowired
    public DbConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public HibernatePersistenceProvider persistenceProvider(){
        return new HibernatePersistenceProvider();
    }

    @Bean
    public ComboPooledDataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(environment.getProperty("db.driverClassName"));
        } catch (PropertyVetoException e) {
            throw new IllegalArgumentException("Wrong driverClass ");
        }

        dataSource.setJdbcUrl(environment.getProperty("db.dataBaseUrl"));
        dataSource.setUser(environment.getProperty("db.userName"));
        dataSource.setPassword(environment.getProperty("db.password"));
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(10);
        dataSource.setMaxIdleTime(200);
        return dataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(ComboPooledDataSource dataSource,
                                                                       HibernatePersistenceProvider persistenceProvider,
                                                                       HibernateJpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("/main/entity");
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setJpaProperties(additionalProperties());
        entityManagerFactory.setPersistenceProvider(persistenceProvider);
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory, ComboPooledDataSource dataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private Properties additionalProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("db.dialect"));
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("db.hbm2ddl.auto"));
        properties.setProperty("hibernate.connection.SetBigStringTryClob", "true");
        properties.setProperty("hibernate.jdbc.batch_size", "1");
        return properties;
    }
}
