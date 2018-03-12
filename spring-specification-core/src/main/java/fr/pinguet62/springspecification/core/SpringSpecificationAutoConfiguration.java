package fr.pinguet62.springspecification.core;

import fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * {@link EnableAutoConfiguration Auto-configuration} of this project.
 * <p>
 * {@link AutoConfigureAfter Executed after} all {@code "SpringSpecification*AutoConfiguration"}.
 */
// @EnableJpaRepositories is depending
@AutoConfigureAfter(SpringSpecificationConfiguration.class)
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "fr.pinguet62.springspecification.core.builder.database.autoconfigure..*"))
public class SpringSpecificationAutoConfiguration {
}
