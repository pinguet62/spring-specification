package fr.pinguet62.springspecification.core.builder.database.autoconfigure.orm.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.*;

/**
 * Like {@link HibernateJpaAutoConfiguration} but:
 * <ul>
 * <li>Declaring custom {@link Bean#name()};</li>
 * <li>Using Spring Specification configuration (see {@link Qualifier}) and properties.</li>
 * </ul>
 *
 * {@link JpaBaseConfiguration} implementation for Hibernate.
 *
 * @author Phillip Webb
 * @author Josh Long
 * @author Manuel Doninger
 * @author Andy Wilkinson
 * @author Stephane Nicoll
 * @since 2.0.0
 */
@Configuration
@ConditionalOnBean(value = DataSource.class, name = DATASOURCE_NAME)
public class SpringSpecificationHibernateJpaConfiguration extends JpaBaseConfiguration {

    private static final String PERSISTENCE_UNIT_NAME = "springSpecification";

    private static final Log logger = LogFactory.getLog(SpringSpecificationHibernateJpaConfiguration.class);

    private static final String JTA_PLATFORM = "hibernate.transaction.jta.platform";

    /**
     * {@code NoJtaPlatform} implementations for various Hibernate versions.
     */
    private static final String[] NO_JTA_PLATFORM_CLASSES = {
            "org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform",
            "org.hibernate.service.jta.platform.internal.NoJtaPlatform" };

    /**
     * {@code WebSphereExtendedJtaPlatform} implementations for various Hibernate
     * versions.
     */
    private static final String[] WEBSPHERE_JTA_PLATFORM_CLASSES = {
            "org.hibernate.engine.transaction.jta.platform.internal.WebSphereExtendedJtaPlatform",
            "org.hibernate.service.jta.platform.internal.WebSphereExtendedJtaPlatform", };

    private final SpringSpecificationHibernateDefaultDdlAutoProvider defaultDdlAutoProvider;

    SpringSpecificationHibernateJpaConfiguration(
            @Qualifier(DATASOURCE_NAME) DataSource dataSource,
            @Qualifier(JPA_PROPERTIES_NAME) JpaProperties jpaProperties,
            ObjectProvider<JtaTransactionManager> jtaTransactionManager,
            ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers,
            ObjectProvider<List<SchemaManagementProvider>> providers) {
        super(dataSource, jpaProperties, jtaTransactionManager,
                transactionManagerCustomizers);
        this.defaultDdlAutoProvider = new SpringSpecificationHibernateDefaultDdlAutoProvider(
                providers.getIfAvailable(Collections::emptyList));
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        Map<String, Object> vendorProperties = new LinkedHashMap<>();
        String defaultDdlMode = this.defaultDdlAutoProvider
                .getDefaultDdlAuto(getDataSource());
        vendorProperties.putAll(getProperties().getHibernateProperties(defaultDdlMode));
        return vendorProperties;
    }

    @Override
    protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
        super.customizeVendorProperties(vendorProperties);
        if (!vendorProperties.containsKey(JTA_PLATFORM)) {
            configureJtaPlatform(vendorProperties);
        }
    }

    private void configureJtaPlatform(Map<String, Object> vendorProperties)
            throws LinkageError {
        JtaTransactionManager jtaTransactionManager = getJtaTransactionManager();
        if (jtaTransactionManager != null) {
            if (runningOnWebSphere()) {
                // We can never use SpringJtaPlatform on WebSphere as
                // WebSphereUowTransactionManager has a null TransactionManager
                // which will cause Hibernate to NPE
                configureWebSphereTransactionPlatform(vendorProperties);
            }
            else {
                configureSpringJtaPlatform(vendorProperties, jtaTransactionManager);
            }
        }
        else {
            vendorProperties.put(JTA_PLATFORM, getNoJtaPlatformManager());
        }
    }

    private boolean runningOnWebSphere() {
        return ClassUtils.isPresent(
                "com.ibm.websphere.jtaextensions." + "ExtendedJTATransaction",
                getClass().getClassLoader());
    }

    private void configureWebSphereTransactionPlatform(
            Map<String, Object> vendorProperties) {
        vendorProperties.put(JTA_PLATFORM, getWebSphereJtaPlatformManager());
    }

    private Object getWebSphereJtaPlatformManager() {
        return getJtaPlatformManager(WEBSPHERE_JTA_PLATFORM_CLASSES);
    }

    private void configureSpringJtaPlatform(Map<String, Object> vendorProperties,
                                            JtaTransactionManager jtaTransactionManager) {
        try {
            vendorProperties.put(JTA_PLATFORM,
                    new SpringJtaPlatform(jtaTransactionManager));
        }
        catch (LinkageError ex) {
            // NoClassDefFoundError can happen if Hibernate 4.2 is used and some
            // containers (e.g. JBoss EAP 6) wraps it in the superclass LinkageError
            if (!isUsingJndi()) {
                throw new IllegalStateException("Unable to set Hibernate JTA "
                        + "platform, are you using the correct "
                        + "version of Hibernate?", ex);
            }
            // Assume that Hibernate will use JNDI
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to set Hibernate JTA platform : " + ex.getMessage());
            }
        }
    }

    private boolean isUsingJndi() {
        try {
            return JndiLocatorDelegate.isDefaultJndiEnvironmentAvailable();
        }
        catch (Error ex) {
            return false;
        }
    }

    private Object getNoJtaPlatformManager() {
        return getJtaPlatformManager(NO_JTA_PLATFORM_CLASSES);
    }

    private Object getJtaPlatformManager(String[] candidates) {
        for (String candidate : candidates) {
            try {
                return Class.forName(candidate).newInstance();
            }
            catch (Exception ex) {
                // Continue searching
            }
        }
        throw new IllegalStateException("Could not configure JTA platform");
    }

    @Bean(TRANSACTION_MANAGER_NAME)
    @ConditionalOnMissingBean(/*value = PlatformTransactionManager.class,*/ name = TRANSACTION_MANAGER_NAME)
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = (JpaTransactionManager) super.transactionManager();
        transactionManager.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        return transactionManager;
    }

    @Bean(JPA_VENDOR_ADAPTER_NAME)
    @ConditionalOnMissingBean(name = JPA_VENDOR_ADAPTER_NAME)
    public JpaVendorAdapter jpaVendorAdapter() {
        return super.jpaVendorAdapter();
    }

    @Bean(ENTITY_MANAGER_FACTORY_BUILDER_NAME)
    @ConditionalOnMissingBean(name = ENTITY_MANAGER_FACTORY_BUILDER_NAME)
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            @Qualifier(JPA_VENDOR_ADAPTER_NAME) JpaVendorAdapter jpaVendorAdapter,
            ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {
        return super.entityManagerFactoryBuilder(jpaVendorAdapter, persistenceUnitManager);
    }

    @Bean(ENTITY_MANAGER_FACTORY_NAME)
    @ConditionalOnMissingBean(name = ENTITY_MANAGER_FACTORY_NAME)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier(ENTITY_MANAGER_FACTORY_BUILDER_NAME) EntityManagerFactoryBuilder factoryBuilder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = super.entityManagerFactory(factoryBuilder);
        entityManagerFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        return entityManagerFactory;
    }

    @Override
    protected String[] getPackagesToScan() {
        return new String[]{"fr.pinguet62.springspecification.core.builder.database"};
    }

}
