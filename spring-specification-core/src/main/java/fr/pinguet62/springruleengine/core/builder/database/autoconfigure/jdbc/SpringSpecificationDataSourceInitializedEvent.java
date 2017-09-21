/*
 * Copyright 2012-2014 the original author or authors.
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

import org.springframework.context.ApplicationEvent;

import javax.sql.DataSource;

/**
 * {@link ApplicationEvent} used internally to trigger {@link DataSource} initialization.
 * Initialization can occur when {@literal schema-*.sql} files are executed or when
 * external libraries (e.g. JPA) initialize the database.
 *
 * @author Dave Syer
 * @see SpringSpecificationDataSourceInitializer
 * @since 1.1.0
 */
@SuppressWarnings("serial")
public class SpringSpecificationDataSourceInitializedEvent extends ApplicationEvent {

	/**
	 * Create a new {@link SpringSpecificationDataSourceInitializedEvent}.
	 * @param source the source {@link DataSource}.
	 */
	public SpringSpecificationDataSourceInitializedEvent(DataSource source) {
		super(source);
	}

}
