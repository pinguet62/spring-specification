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

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE;

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
//	@ConditionalOnSingleCandidate(HikariDataSource.class)
//	static class Hikari {
//
//		private final HikariDataSource dataSource;
//
//		private final ObjectProvider<MBeanExporter> mBeanExporter;
//
//		Hikari(@Qualifier(DATASOURCE) HikariDataSource dataSource, ObjectProvider<MBeanExporter> mBeanExporter) {
//			this.dataSource = dataSource;
//			this.mBeanExporter = mBeanExporter;
//		}
//
//		@PostConstruct
//		public void validateMBeans() {
//			MBeanExporter exporter = this.mBeanExporter.getIfUnique();
//			if (exporter != null && this.dataSource.isRegisterMbeans()) {
//				exporter.addExcludedBean(DATASOURCE);
//			}
//		}
//
//	}

	@Configuration
	@ConditionalOnProperty(prefix = "spring-specification.datasource", name = "jmx-enabled")
	@ConditionalOnClass(name = "org.apache.tomcat.jdbc.pool.DataSourceProxy")
	@ConditionalOnSingleCandidate(DataSource.class)
	static class TomcatDataSourceJmxConfiguration {

		@Bean
		@ConditionalOnMissingBean(name = "dataSourceMBean")
		public Object dataSourceMBean(@Qualifier(DATASOURCE) DataSource dataSource) {
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
