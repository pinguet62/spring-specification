package fr.pinguet62.springruleengine.core.builder.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.ENTITY_MANAGER_FACTORY;
import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.TRANSACTION_MANAGER;

@Configuration
@EnableJpaRepositories(/*TODO fix: basePackageClasses = SpringDataJpaConfig.class,*/ entityManagerFactoryRef = ENTITY_MANAGER_FACTORY, transactionManagerRef = TRANSACTION_MANAGER)
public class SpringDataJpaConfig {
}
