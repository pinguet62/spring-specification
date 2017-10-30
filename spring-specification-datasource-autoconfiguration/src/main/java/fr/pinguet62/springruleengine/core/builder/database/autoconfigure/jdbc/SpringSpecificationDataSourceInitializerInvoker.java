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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import javax.sql.DataSource;

import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_NAME;
import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_PROPERTIES_NAME;

/**
 * Bean to handle {@link DataSource} initialization by running {@literal spring-specification/schema-*.sql} on
 * {@link InitializingBean#afterPropertiesSet()} and {@literal spring-specification/data-*.sql} SQL scripts on
 * a {@link SpringSpecificationDataSourceSchemaCreatedEvent}.
 *
 * @author Stephane Nicoll
 * @see SpringSpecificationDataSourceAutoConfiguration
 */
class SpringSpecificationDataSourceInitializerInvoker
        implements ApplicationListener<SpringSpecificationDataSourceSchemaCreatedEvent>, InitializingBean {

    private static final Log logger = LogFactory
			.getLog(SpringSpecificationDataSourceInitializerInvoker.class);

	private final ObjectProvider<DataSource> dataSource;

	private final DataSourceProperties properties;

	private final ApplicationContext applicationContext;

	private SpringSpecificationDataSourceInitializer dataSourceInitializer;

	private boolean initialized;

	SpringSpecificationDataSourceInitializerInvoker(@Qualifier(DATASOURCE_NAME) ObjectProvider<DataSource> dataSource,
                                                    @Qualifier(DATASOURCE_PROPERTIES_NAME) DataSourceProperties properties, ApplicationContext applicationContext) {
		this.dataSource = dataSource;
		this.properties = properties;
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() {
		SpringSpecificationDataSourceInitializer initializer = getDataSourceInitializer();
		if (initializer != null) {
			boolean schemaCreated = this.dataSourceInitializer.createSchema();
			if (schemaCreated) {
				initialize(initializer);
			}
		}
	}

	private void initialize(SpringSpecificationDataSourceInitializer initializer) {
		try {
			this.applicationContext.publishEvent(
					new SpringSpecificationDataSourceSchemaCreatedEvent(initializer.getDataSource()));
			// The listener might not be registered yet, so don't rely on it.
			if (!this.initialized) {
				this.dataSourceInitializer.initSchema();
				this.initialized = true;
			}
		}
		catch (IllegalStateException ex) {
			logger.warn("Could not send event to complete DataSource initialization ("
					+ ex.getMessage() + ")");
		}
	}

	@Override
	public void onApplicationEvent(SpringSpecificationDataSourceSchemaCreatedEvent event) {
		// NOTE the event can happen more than once and
		// the event datasource is not used here
		SpringSpecificationDataSourceInitializer initializer = getDataSourceInitializer();
		if (!this.initialized && initializer != null) {
			initializer.initSchema();
			this.initialized = true;
		}
	}

	private SpringSpecificationDataSourceInitializer getDataSourceInitializer() {
		if (this.dataSourceInitializer == null) {
			DataSource ds = this.dataSource.getIfUnique();
			if (ds != null) {
				this.dataSourceInitializer = new SpringSpecificationDataSourceInitializer(ds,
						this.properties, this.applicationContext);
			}
		}
		return this.dataSourceInitializer;
	}

}
