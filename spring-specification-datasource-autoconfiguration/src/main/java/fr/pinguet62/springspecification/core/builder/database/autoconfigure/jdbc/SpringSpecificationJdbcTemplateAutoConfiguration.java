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

package fr.pinguet62.springspecification.core.builder.database.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.*;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link JdbcTemplate} and
 * {@link NamedParameterJdbcTemplate}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @author Kazuki Shimizu
 * @since 1.4.0
 */
@Configuration
@ConditionalOnClass({ DataSource.class, JdbcTemplate.class })
@ConditionalOnBean(/*value = DataSource.class,*/ name = DATASOURCE_NAME)
@AutoConfigureAfter({ SpringSpecificationDataSourceAutoConfiguration.class, org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration.class })
@EnableConfigurationProperties(SpringSpecificationJdbcProperties.class)
public class SpringSpecificationJdbcTemplateAutoConfiguration {

	@Configuration
	static class JdbcTemplateConfiguration {

		private final DataSource dataSource;

		private final SpringSpecificationJdbcProperties properties;

		JdbcTemplateConfiguration(@Qualifier(DATASOURCE_NAME) DataSource dataSource, SpringSpecificationJdbcProperties properties) {
			this.dataSource = dataSource;
			this.properties = properties;
		}

		@Bean(JDBC_TEMPLATE_NAME)
		// @Primary
		@ConditionalOnMissingBean(/*value = JdbcOperations.class,*/ name = JDBC_OPERATIONS_NAME)
		public JdbcTemplate jdbcTemplate() {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
			SpringSpecificationJdbcProperties.Template template = this.properties.getTemplate();
			jdbcTemplate.setFetchSize(template.getFetchSize());
			jdbcTemplate.setMaxRows(template.getMaxRows());
			if (template.getQueryTimeout() != null) {
				jdbcTemplate
						.setQueryTimeout((int) template.getQueryTimeout().getSeconds());
			}
			return jdbcTemplate;
		}

	}

	@Configuration
	@Import(JdbcTemplateConfiguration.class)
	static class NamedParameterJdbcTemplateConfiguration {

		@Bean(NAMED_PARAMETER_JDBC_TEMPLATE_NAME)
		// @Primary
		@ConditionalOnBean(/*value = JdbcTemplate.class,*/ name = JDBC_TEMPLATE_NAME)
		@ConditionalOnMissingBean(/*value = NamedParameterJdbcOperations.class,*/ name = NAMED_PARAMETER_JDBC_TEMPLATE_NAME)
		public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
				JdbcTemplate jdbcTemplate) {
			return new NamedParameterJdbcTemplate(jdbcTemplate);
		}

	}

}
