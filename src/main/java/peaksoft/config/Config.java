package peaksoft.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:application.properties")
public class Config {

    private final Environment environment;

    @Autowired
    public Config(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public JpaVendorAdapter vendorAdapter() {
        return new HibernateJpaVendorAdapter();

    }

    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver-classname"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.passport"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager getTransaction() {
        return new JpaTransactionManager(Objects.requireNonNull(factoryBean().getObject()));

    }

    @Bean
    public Properties getProperty() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean factoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter());
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("peaksoft");
        factoryBean.setJpaProperties(getProperty());
        return factoryBean;
    }

}