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

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_MBEAN_NAME;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_NAME;

/**
 * Configures DataSource related MBeans.
 *
 * @author Stephane Nicoll
 */
@Configuration
@ConditionalOnProperty(prefix = "spring-specification.jmx", name = "enabled", havingValue = "true", matchIfMissing = true)
class SpringSpecificationDataSourceJmxConfiguration {

	private static final Log logger = LogFactory.getLog(SpringSpecificationDataSourceJmxConfiguration.class);

//	@Configuration
//	@ConditionalOnClass(HikariDataSource.class)
//	@ConditionalOnSingleCandidate(DataSource.class)
//	static class Hikari {
//
//		private final DataSource dataSource;
//
//		private final ObjectProvider<MBeanExporter> mBeanExporter;
//
//		Hikari(@Qualifier(DATASOURCE_NAME) DataSource dataSource, ObjectProvider<MBeanExporter> mBeanExporter) {
//			this.dataSource = dataSource;
//			this.mBeanExporter = mBeanExporter;
//		}
//
//		@PostConstruct
//		public void validateMBeans() {
//			HikariDataSource hikariDataSource = unwrapHikariDataSource();
//			if (hikariDataSource != null && hikariDataSource.isRegisterMbeans()) {
//				this.mBeanExporter
//						.ifUnique((exporter) -> exporter.addExcludedBean("dataSource"));
//			}
//		}
//
//		private HikariDataSource unwrapHikariDataSource() {
//			try {
//				return this.dataSource.unwrap(HikariDataSource.class);
//			}
//			catch (SQLException ex) {
//				return null;
//			}
//		}
//
//	}

	@Configuration
	@ConditionalOnProperty(prefix = "spring-specification.datasource", name = "jmx-enabled")
	@ConditionalOnClass(name = "org.apache.tomcat.jdbc.pool.DataSourceProxy")
	@ConditionalOnBean(/*value = DataSource.class,*/ name = DATASOURCE_NAME)
	static class TomcatDataSourceJmxConfiguration {

		@Bean(DATASOURCE_MBEAN_NAME)
		@ConditionalOnMissingBean(name = DATASOURCE_MBEAN_NAME)
		public Object dataSourceMBean(@Qualifier(DATASOURCE_NAME) DataSource dataSource) {
			if (dataSource instanceof DataSourceProxy) {
				try {
					return ((DataSourceProxy) dataSource).createPool().getJmxPool();
				}
				catch (SQLException ex) {
					logger.warn("Cannot expose DataSource to JMX (could not connect)");
				}
			}
			return null;
		}

	}

}
