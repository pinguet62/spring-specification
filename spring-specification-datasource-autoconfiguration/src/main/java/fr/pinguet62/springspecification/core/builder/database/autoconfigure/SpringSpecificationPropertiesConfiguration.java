package fr.pinguet62.springspecification.core.builder.database.autoconfigure;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_PROPERTIES_NAME;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.JPA_PROPERTIES_NAME;

/**
 * Custom property groups, to create auto-configuration based on properties
 * (like Spring Boot using {@code spring.*}).
 */
@Configuration
public class SpringSpecificationPropertiesConfiguration {

    @Bean(DATASOURCE_PROPERTIES_NAME)
    @ConfigurationProperties(prefix = "spring-specification.datasource")
    public DataSourceProperties springSpecificationDataSourceProperties() {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setName("springSpecification"); // @Override default
        return dataSourceProperties;
    }

    @Bean(JPA_PROPERTIES_NAME)
    @ConfigurationProperties(prefix = "spring-specification.jpa")
    public JpaProperties springSpecificationJpaProperties() {
        return new JpaProperties();
    }

}
