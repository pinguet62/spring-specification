package fr.pinguet62.springruleengine.core;

import fr.pinguet62.springruleengine.core.builder.database.autoconfigure.jdbc.SpringSpecificationDataSourceAutoConfiguration;
import fr.pinguet62.springruleengine.core.builder.database.autoconfigure.jpa.SpringSpecificationHibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@link EnableAutoConfiguration Auto-configuration} of this project.
 * <p>
 * {@link AutoConfigureAfter Executed after} all {@code "SpringSpecification*AutoConfiguration"}.
 */
// @EnableJpaRepositories is depending
@AutoConfigureAfter({SpringSpecificationDataSourceAutoConfiguration.class, SpringSpecificationHibernateJpaAutoConfiguration.class})
@ComponentScan
public class SpringSpecificationAutoConfiguration {
}
