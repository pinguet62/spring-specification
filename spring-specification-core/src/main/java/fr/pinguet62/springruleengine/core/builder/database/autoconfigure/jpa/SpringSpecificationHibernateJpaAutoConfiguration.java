package fr.pinguet62.springruleengine.core.builder.database.autoconfigure.jpa;

import fr.pinguet62.springruleengine.core.builder.database.SpringDataJpaConfig;
import fr.pinguet62.springruleengine.core.builder.database.autoconfigure.jdbc.SpringSpecificationDataSourceAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.*;

/**
 * Like {@link HibernateJpaAutoConfiguration} but:
 * <ul>
 * <li>Declaring custom {@link Bean#name()};</li>
 * <li>Using Spring Specification configuration (see {@link Qualifier}) and properties.</li>
 * </ul>
 */
@Configuration
@AutoConfigureAfter({SpringSpecificationDataSourceAutoConfiguration.class})
@Import(SpringSpecificationDataSourceInitializedPublisher.Registrar.class) // see org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
public class SpringSpecificationHibernateJpaAutoConfiguration extends HibernateJpaAutoConfiguration {

    private static final String PERSISTENCE_UNIT_NAME = "springSpecification";

    public SpringSpecificationHibernateJpaAutoConfiguration(
            @Qualifier(DATASOURCE) DataSource dataSource,
            @Qualifier(JPA_PROPERTIES) JpaProperties jpaProperties,
            ObjectProvider<JtaTransactionManager> jtaTransactionManager,
            ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, jpaProperties, jtaTransactionManager, transactionManagerCustomizers);
    }

    @Bean(TRANSACTION_MANAGER)
    @ConditionalOnMissingBean(/*value = PlatformTransactionManager.class,*/ name = TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = (JpaTransactionManager) super.transactionManager();
        transactionManager.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        return transactionManager;
    }

    @Bean(JPA_VENDOR_ADAPTER)
    @ConditionalOnMissingBean(name = JPA_VENDOR_ADAPTER)
    public JpaVendorAdapter jpaVendorAdapter() {
        return super.jpaVendorAdapter();
    }

    @Bean(ENTITY_MANAGER_FACTORY_BUILDER)
    @ConditionalOnMissingBean(name = ENTITY_MANAGER_FACTORY_BUILDER)
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            @Qualifier(JPA_VENDOR_ADAPTER) JpaVendorAdapter jpaVendorAdapter,
            ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {
        return super.entityManagerFactoryBuilder(jpaVendorAdapter, persistenceUnitManager);
    }

    @Bean(ENTITY_MANAGER_FACTORY)
    @ConditionalOnMissingBean(name = ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier(ENTITY_MANAGER_FACTORY_BUILDER) EntityManagerFactoryBuilder factoryBuilder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = super.entityManagerFactory(factoryBuilder);
        entityManagerFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        return entityManagerFactory;
    }

    @Override
    protected String[] getPackagesToScan() {
        return new String[]{SpringDataJpaConfig.class.getPackage().getName()};
    }

}
