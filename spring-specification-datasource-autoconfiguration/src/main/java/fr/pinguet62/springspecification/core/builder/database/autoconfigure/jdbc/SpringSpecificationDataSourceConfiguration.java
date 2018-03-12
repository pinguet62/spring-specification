/*
 * Copyright 2012-2018 the original author or authors.
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

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_NAME;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_PROPERTIES_NAME;

/**
 * Actual DataSource configurations imported by {@link SpringSpecificationDataSourceAutoConfiguration}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Stephane Nicoll
 */
abstract class SpringSpecificationDataSourceConfiguration {

	@SuppressWarnings("unchecked")
	protected <T> T createDataSource(SpringSpecificationDataSourceProperties properties,
			Class<? extends DataSource> type) {
		return (T) properties.initializeDataSourceBuilder().type(type).build();
	}

	/**
	 * Tomcat Pool DataSource configuration.
	 */
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	@ConditionalOnProperty(name = "spring-specification.datasource.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource", matchIfMissing = true)
	static class Tomcat extends SpringSpecificationDataSourceConfiguration {

		@Bean(DATASOURCE_NAME)
		@ConfigurationProperties(prefix = "spring-specification.datasource.tomcat")
		public org.apache.tomcat.jdbc.pool.DataSource dataSource(
				SpringSpecificationDataSourceProperties properties) {
			org.apache.tomcat.jdbc.pool.DataSource dataSource = createDataSource(
					properties, org.apache.tomcat.jdbc.pool.DataSource.class);
			DatabaseDriver databaseDriver = DatabaseDriver
					.fromJdbcUrl(properties.determineUrl());
			String validationQuery = databaseDriver.getValidationQuery();
			if (validationQuery != null) {
				dataSource.setTestOnBorrow(true);
				dataSource.setValidationQuery(validationQuery);
			}
			return dataSource;
		}

	}

	/**
	 * Hikari DataSource configuration.
	 */
	@ConditionalOnClass(HikariDataSource.class)
	@ConditionalOnProperty(name = "spring-specification.datasource.type", havingValue = "com.zaxxer.hikari.HikariDataSource", matchIfMissing = true)
	static class Hikari extends SpringSpecificationDataSourceConfiguration {

		@Bean(DATASOURCE_NAME)
		@ConfigurationProperties(prefix = "spring-specification.datasource.hikari")
		public HikariDataSource dataSource(SpringSpecificationDataSourceProperties properties) {
			HikariDataSource dataSource = createDataSource(properties,
					HikariDataSource.class);
			if (StringUtils.hasText(properties.getName())) {
				dataSource.setPoolName(properties.getName());
			}
			return dataSource;
		}

	}

	/**
	 * DBCP DataSource configuration.
	 */
	@ConditionalOnClass(org.apache.commons.dbcp2.BasicDataSource.class)
	@ConditionalOnProperty(name = "spring-specification.datasource.type", havingValue = "org.apache.commons.dbcp2.BasicDataSource", matchIfMissing = true)
	static class Dbcp2 extends SpringSpecificationDataSourceConfiguration {

		@Bean(DATASOURCE_NAME)
		@ConfigurationProperties(prefix = "spring-specification.datasource.dbcp2")
		public org.apache.commons.dbcp2.BasicDataSource dataSource(
				SpringSpecificationDataSourceProperties properties) {
			return createDataSource(properties,
					org.apache.commons.dbcp2.BasicDataSource.class);
		}

	}

	/**
	 * Generic DataSource configuration.
	 */
	@ConditionalOnMissingBean(/*value = DataSource.class,*/ name = DATASOURCE_NAME)
	@ConditionalOnProperty(name = "spring-specification.datasource.type")
	static class Generic {

		@Bean(DATASOURCE_NAME)
		public DataSource dataSource(SpringSpecificationDataSourceProperties properties) {
			return properties.initializeDataSourceBuilder().build();
		}

	}

}
