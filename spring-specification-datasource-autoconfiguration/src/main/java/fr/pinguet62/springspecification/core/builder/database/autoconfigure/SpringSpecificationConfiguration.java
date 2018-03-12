package fr.pinguet62.springspecification.core.builder.database.autoconfigure;

import fr.pinguet62.springspecification.core.builder.database.autoconfigure.orm.jpa.SpringSpecificationHibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Import;

@AutoConfigureAfter(SpringSpecificationHibernateJpaAutoConfiguration.class)
@Import(FixPrimaryBeanDefinitionRegistryPostProcessor.class)
public class SpringSpecificationConfiguration {
}
