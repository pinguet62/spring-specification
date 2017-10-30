package fr.pinguet62.springruleengine.core.builder.database;

import fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(/*TODO fix: basePackageClasses = SpringDataJpaConfig.class,*/ entityManagerFactoryRef = SpringSpecificationBeans.ENTITY_MANAGER_FACTORY_NAME, transactionManagerRef = SpringSpecificationBeans.TRANSACTION_MANAGER_NAME)
public class SpringDataJpaConfig {
}
