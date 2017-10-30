/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.pinguet62.springruleengine.core.builder.database.autoconfigure.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.support.JmxUtils;

import javax.sql.DataSource;

import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_NAME;
import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_PROPERTIES_NAME;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for a JNDI located
 * {@link DataSource}.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @since 1.2.0
 */
@Configuration
@AutoConfigureBefore({ SpringSpecificationXADataSourceAutoConfiguration.class,
		SpringSpecificationDataSourceAutoConfiguration.class })
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@ConditionalOnProperty(prefix = "spring-specification.datasource", name = "jndi-name")
@EnableConfigurationProperties(DataSourceProperties.class)
public class SpringSpecificationJndiDataSourceAutoConfiguration {

	private final ApplicationContext context;

	public SpringSpecificationJndiDataSourceAutoConfiguration(ApplicationContext context) {
		this.context = context;
	}

	@Bean(name = DATASOURCE_NAME, destroyMethod = "")
	@ConditionalOnMissingBean(name = DATASOURCE_NAME)
	public DataSource dataSource(@Qualifier(DATASOURCE_PROPERTIES_NAME) DataSourceProperties properties) {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		DataSource dataSource = dataSourceLookup.getDataSource(properties.getJndiName());
		excludeMBeanIfNecessary(dataSource, DATASOURCE_NAME);
		return dataSource;
	}

	private void excludeMBeanIfNecessary(Object candidate, String beanName) {
		for (MBeanExporter mbeanExporter : this.context
				.getBeansOfType(MBeanExporter.class).values()) {
			if (JmxUtils.isMBean(candidate.getClass())) {
				mbeanExporter.addExcludedBean(beanName);
			}
		}
	}

}
