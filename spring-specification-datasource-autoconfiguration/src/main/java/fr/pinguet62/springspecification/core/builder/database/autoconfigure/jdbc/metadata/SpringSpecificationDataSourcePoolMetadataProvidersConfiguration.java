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

package fr.pinguet62.springspecification.core.builder.database.autoconfigure.jdbc.metadata;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.TomcatDataSourcePoolMetadata;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.COMMONSDBCP2_POOL_DATASOURCE_METADATA_PROVIDER_NAME;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.HIKARI_POOL_DATASOURCE_METADATA_PROVIDER_NAME;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.TOMCAT_POOL_DATASOURCE_METADATA_PROVIDER_NAME;

/**
 * Register the {@link DataSourcePoolMetadataProvider} instances for the supported data
 * sources.
 *
 * @author Stephane Nicoll
 * @since 1.2.0
 */
@Configuration
public class SpringSpecificationDataSourcePoolMetadataProvidersConfiguration {

	@Configuration
	@ConditionalOnClass(org.apache.tomcat.jdbc.pool.DataSource.class)
	static class TomcatDataSourcePoolMetadataProviderConfiguration {

		@Bean(TOMCAT_POOL_DATASOURCE_METADATA_PROVIDER_NAME)
		@ConditionalOnMissingBean(name = TOMCAT_POOL_DATASOURCE_METADATA_PROVIDER_NAME)
		public DataSourcePoolMetadataProvider tomcatPoolDataSourceMetadataProvider() {
			return (dataSource) -> {
				if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
					return new TomcatDataSourcePoolMetadata(
							(org.apache.tomcat.jdbc.pool.DataSource) dataSource);
				}
				return null;
			};
		}

	}

	@Configuration
	@ConditionalOnClass(HikariDataSource.class)
	static class HikariPoolDataSourceMetadataProviderConfiguration {

		@Bean(HIKARI_POOL_DATASOURCE_METADATA_PROVIDER_NAME)
		@ConditionalOnMissingBean(name = HIKARI_POOL_DATASOURCE_METADATA_PROVIDER_NAME)
		public DataSourcePoolMetadataProvider hikariPoolDataSourceMetadataProvider() {
			return (dataSource) -> {
				if (dataSource instanceof HikariDataSource) {
					return new HikariDataSourcePoolMetadata(
							(HikariDataSource) dataSource);
				}
				return null;
			};
		}

	}

	@Configuration
	@ConditionalOnClass(BasicDataSource.class)
	static class CommonsDbcp2PoolDataSourceMetadataProviderConfiguration {

		@Bean(COMMONSDBCP2_POOL_DATASOURCE_METADATA_PROVIDER_NAME)
		@ConditionalOnMissingBean(name = COMMONSDBCP2_POOL_DATASOURCE_METADATA_PROVIDER_NAME)
		public DataSourcePoolMetadataProvider commonsDbcp2PoolDataSourceMetadataProvider() {
			return (dataSource) -> {
				if (dataSource instanceof BasicDataSource) {
					return new CommonsDbcp2DataSourcePoolMetadata(
							(BasicDataSource) dataSource);
				}
				return null;
			};
		}

	}

}
