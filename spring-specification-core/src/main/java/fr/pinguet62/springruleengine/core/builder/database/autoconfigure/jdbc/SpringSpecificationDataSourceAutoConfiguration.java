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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.util.Arrays;

import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.*;
import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.jdbc.SpringSpecificationDataSourceInitializerPostProcessor.Registrar;
import static java.util.function.Predicate.isEqual;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link DataSource}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @author Kazuki Shimizu
 */
@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class}) // let Spring Boot create auto-configuration beans
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@EnableConfigurationProperties(DataSourceProperties.class)
@Import({ Registrar.class/*, DataSourcePoolMetadataProvidersConfiguration.class*/ })
public class SpringSpecificationDataSourceAutoConfiguration {

	private static final Log logger = LogFactory
			.getLog(SpringSpecificationDataSourceAutoConfiguration.class);

	@Bean(DATASOURCE_INITIALIZER)
	@ConditionalOnMissingBean(name = DATASOURCE_INITIALIZER)
	public SpringSpecificationDataSourceInitializer dataSourceInitializer(@Qualifier(DATASOURCE_PROPERTIES) DataSourceProperties properties,
			ApplicationContext applicationContext) {
		return new SpringSpecificationDataSourceInitializer(properties, applicationContext);
	}

//	/**
//	 * Determines if the {@code dataSource} being used by Spring was created from
//	 * {@link SpringSpecificationEmbeddedDataSourceConfiguration}.
//	 * @param beanFactory the bean factory
//	 * @return true if the data source was auto-configured.
//	 */
//	public static boolean containsAutoConfiguredDataSource(
//			ConfigurableListableBeanFactory beanFactory) {
//		try {
//			BeanDefinition beanDefinition = beanFactory.getBeanDefinition(DATASOURCE);
//			return SpringSpecificationEmbeddedDataSourceConfiguration.class.getName()
//					.equals(beanDefinition.getFactoryBeanName());
//		}
//		catch (NoSuchBeanDefinitionException ex) {
//			return false;
//		}
//	}

	@Configuration
	@Conditional(EmbeddedDatabaseCondition.class)
	@ConditionalOnMissingBean(value = { DataSource.class, XADataSource.class }, name = DATASOURCE)
	@Import(SpringSpecificationEmbeddedDataSourceConfiguration.class)
	protected static class EmbeddedDatabaseConfiguration {

	}

	@Configuration
	@Conditional(PooledDataSourceCondition.class)
	@ConditionalOnMissingBean(value = { DataSource.class, XADataSource.class }, name = DATASOURCE)
	@Import({ SpringSpecificationDataSourceConfiguration.Tomcat.class, SpringSpecificationDataSourceConfiguration.Hikari.class,
			SpringSpecificationDataSourceConfiguration.Dbcp.class, SpringSpecificationDataSourceConfiguration.Dbcp2.class,
			SpringSpecificationDataSourceConfiguration.Generic.class })
	@SuppressWarnings("deprecation")
	protected static class PooledDataSourceConfiguration {

	}

//	@Configuration
//	@ConditionalOnProperty(prefix = "springSpecification.datasource", name = "jmx-enabled")
//	@ConditionalOnClass(name = "org.apache.tomcat.jdbc.pool.DataSourceProxy")
//	@Conditional(SpringSpecificationDataSourceAutoConfiguration.DataSourceAvailableCondition.class)
//	@ConditionalOnMissingBean(name = "dataSourceMBean")
//	protected static class TomcatDataSourceJmxConfiguration {
//
//		@Bean
//		public Object dataSourceMBean(DataSource dataSource) {
//			if (dataSource instanceof DataSourceProxy) {
//				try {
//					return ((DataSourceProxy) dataSource).createPool().getJmxPool();
//				}
//				catch (SQLException ex) {
//					logger.warn("Cannot expose DataSource to JMX (could not connect)");
//				}
//			}
//			return null;
//		}
//
//	}

	/**
	 * {@link AnyNestedCondition} that checks that either {@code spring.datasource.type}
	 * is set or {@link PooledDataSourceAvailableCondition} applies.
	 */
	static class PooledDataSourceCondition extends AnyNestedCondition {

		PooledDataSourceCondition() {
			super(ConfigurationPhase.PARSE_CONFIGURATION);
		}

		@ConditionalOnProperty(prefix = "springSpecification.datasource", name = "type")
		static class ExplicitType {

		}

		@Conditional(PooledDataSourceAvailableCondition.class)
		static class PooledDataSourceAvailable {

		}

	}

	/**
	 * {@link Condition} to test if a supported connection pool is available.
	 */
	static class PooledDataSourceAvailableCondition extends SpringBootCondition {

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("PooledDataSource");
			if (getDataSourceClassLoader(context) != null) {
				return ConditionOutcome
						.match(message.foundExactly("supported DataSource"));
			}
			return ConditionOutcome
					.noMatch(message.didNotFind("supported DataSource").atAll());
		}

		/**
		 * Returns the class loader for the {@link DataSource} class. Used to ensure that
		 * the driver class can actually be loaded by the data source.
		 * @param context the condition context
		 * @return the class loader
		 */
		private ClassLoader getDataSourceClassLoader(ConditionContext context) {
			Class<?> dataSourceClass = new DataSourceBuilder(context.getClassLoader())
					.findType();
			return (dataSourceClass == null ? null : dataSourceClass.getClassLoader());
		}

	}

	/**
	 * {@link Condition} to detect when an embedded {@link DataSource} type can be used.
	 * If a pooled {@link DataSource} is available, it will always be preferred to an
	 * {@code EmbeddedDatabase}.
	 */
	static class EmbeddedDatabaseCondition extends SpringBootCondition {

		private final SpringBootCondition pooledCondition = new PooledDataSourceCondition();

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("EmbeddedDataSource");
			if (anyMatches(context, metadata, this.pooledCondition)) {
				return ConditionOutcome
						.noMatch(message.foundExactly("supported pooled data source"));
			}
			EmbeddedDatabaseType type = EmbeddedDatabaseConnection
					.get(context.getClassLoader()).getType();
			if (type == null) {
				return ConditionOutcome
						.noMatch(message.didNotFind("embedded database").atAll());
			}
			return ConditionOutcome.match(message.found("embedded database").items(type));
		}

	}

	/**
	 * {@link Condition} to detect when a {@link DataSource} is available (either because
	 * the user provided one or because one will be auto-configured).
	 */
	@Order(Ordered.LOWEST_PRECEDENCE - 10)
	static class DataSourceAvailableCondition extends SpringBootCondition {

		private final SpringBootCondition pooledCondition = new PooledDataSourceCondition();

		private final SpringBootCondition embeddedCondition = new EmbeddedDatabaseCondition();

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context,
				AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage
					.forCondition("DataSourceAvailable");
			if (hasBean(context, DataSource.class, DATASOURCE)
					|| hasBean(context, XADataSource.class, XA_DATASOURCE)) {
				return ConditionOutcome
						.match(message.foundExactly("existing data source bean"));
			}
			if (anyMatches(context, metadata, this.pooledCondition,
					this.embeddedCondition)) {
				return ConditionOutcome.match(message
						.foundExactly("existing auto-configured data source bean"));
			}
			return ConditionOutcome
					.noMatch(message.didNotFind("any existing data source bean").atAll());
		}

		private boolean hasBean(ConditionContext context, Class<?> type, String beanName) {
			return Arrays
					.stream(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(context.getBeanFactory(), type, true, false))
					.filter(isEqual(beanName))
					.count() > 0;
		}

	}

}
