package com.adben.testdatabuilder.entity;

import static com.adben.testdatabuilder.entity.EntityDataBuilderFactory.createEntityDataBuilder;

import com.adben.testdatabuilder.core.DataBuilder;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.Driver;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaTestConfiguration {

  private static final String PACKAGE_SCAN = "com.adben";
  private static final String PERSISTENCE_UNIT_NAME = "persistenceUnit";

  @PersistenceContext
  private EntityManager em;

  @Bean
  public DataBuilder entityBuilder() {
    return createEntityDataBuilder(this.em);
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    final LocalContainerEntityManagerFactoryBean lcemfb =
        new LocalContainerEntityManagerFactoryBean();

    lcemfb.setDataSource(dataSource());
    lcemfb.setPackagesToScan(PACKAGE_SCAN);
    lcemfb.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);

    final JpaVendorAdapter va = new HibernateJpaVendorAdapter();
    lcemfb.setJpaVendorAdapter(va);

    final Properties ps = new Properties();
    ps.setProperty("hibernate.ejb.entitymanager_factory_name", PERSISTENCE_UNIT_NAME);
    ps.setProperty("hibernate.dialect", MySQL5InnoDBDialect.class.getCanonicalName());
    ps.setProperty("hibernate.hbm2ddl.auto", "create");
    ps.setProperty("hibernate.show_sql", "true");
    lcemfb.setJpaProperties(ps);

    lcemfb.afterPropertiesSet();

    return lcemfb;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {

    final JpaTransactionManager tm = new JpaTransactionManager();

    tm.setEntityManagerFactory(this.entityManagerFactoryBean().getObject());

    return tm;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  private static DataSource dataSource() {

    final BasicDataSource ds = new BasicDataSource();

    ds.setDriverClassName(Driver.class.getCanonicalName());
    ds.setUrl("jdbc:h2:mem:todo;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

    return ds;
  }

}
