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

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_NAME;

/**
 * Configuration for embedded data sources.
 *
 * @author Phillip Webb
 * @author Stephane Nicoll
 * @see SpringSpecificationDataSourceAutoConfiguration
 */
@Configuration
@EnableConfigurationProperties(SpringSpecificationDataSourceProperties.class)
public class SpringSpecificationEmbeddedDataSourceConfiguration implements BeanClassLoaderAware {

	private EmbeddedDatabase database;

	private ClassLoader classLoader;

	private final SpringSpecificationDataSourceProperties properties;

	public SpringSpecificationEmbeddedDataSourceConfiguration(SpringSpecificationDataSourceProperties properties) {
		this.properties = properties;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Bean(DATASOURCE_NAME)
	public EmbeddedDatabase dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseConnection.get(this.classLoader).getType())
				.setName(this.properties.determineDatabaseName());
		this.database = builder.build();
		return this.database;
	}

	@PreDestroy
	public void close() {
		if (this.database != null) {
			this.database.shutdown();
		}
	}

}
