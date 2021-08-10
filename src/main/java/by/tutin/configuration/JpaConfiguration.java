package by.tutin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class JpaConfiguration {
    @Value("${database.databaseUrl}")
    private String databaseUrl;
    @Value("${database.username}")
    private String username;
    @Value("${database.password}")
    private String password;
    @Value("${database.driver_Class_name}")
    private String driverClassName;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.show_sql}")
    private String showSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;
    @Value("${hibernate.lazy_load_no_trans}")
    private String hibernateLazyLoadNoTrans;
    @Value("${hibernate.use_jbl_metadata_defaults}")
    private String hibernateTempUseJdbcMetadataDefaults;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource driverManager = new DriverManagerDataSource(databaseUrl,username,password);
        driverManager.setDriverClassName(driverClassName);
        return driverManager;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("by.tutin.model");
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        entityManager.setJpaProperties(gerProperties());

        return entityManager;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;

    }

    private Properties gerProperties(){
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect",dialect);
        properties.setProperty("hibernate.show_sql",showSql);
        properties.setProperty("hibernate.hbm2ddl.auto",hibernateHbm2ddlAuto);
        properties.setProperty("hibernate.lazy_load_no_trans",hibernateLazyLoadNoTrans);
        properties.setProperty("hibernate.use_jbl_metadata_defaults",hibernateTempUseJdbcMetadataDefaults);

        return properties;
    }

}
