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

package fr.pinguet62.springspecification.core.builder.database.autoconfigure.orm.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;

/**
 * Settings to apply when configuring Hibernate.
 *
 * @author Andy Wilkinson
 * @since 2.0.0
 */
public class SpringSpecificationHibernateSettings {

	private Supplier<String> ddlAuto;

	private ImplicitNamingStrategy implicitNamingStrategy;

	private PhysicalNamingStrategy physicalNamingStrategy;

	private Collection<SpringSpecificationHibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

	public SpringSpecificationHibernateSettings ddlAuto(Supplier<String> ddlAuto) {
		this.ddlAuto = ddlAuto;
		return this;
	}

	/**
	 * Specify the default ddl auto value to use.
	 * @param ddlAuto the default ddl auto if none is provided
	 * @return this instance
	 * @see #ddlAuto(Supplier)
	 * @deprecated as of 2.0.1 in favour of {@link #ddlAuto(Supplier)}
	 */
	@Deprecated
	public SpringSpecificationHibernateSettings ddlAuto(String ddlAuto) {
		return ddlAuto(() -> ddlAuto);
	}

	public String getDdlAuto() {
		return (this.ddlAuto != null ? this.ddlAuto.get() : null);
	}

	public SpringSpecificationHibernateSettings implicitNamingStrategy(
			ImplicitNamingStrategy implicitNamingStrategy) {
		this.implicitNamingStrategy = implicitNamingStrategy;
		return this;
	}

	public ImplicitNamingStrategy getImplicitNamingStrategy() {
		return this.implicitNamingStrategy;
	}

	public SpringSpecificationHibernateSettings physicalNamingStrategy(
			PhysicalNamingStrategy physicalNamingStrategy) {
		this.physicalNamingStrategy = physicalNamingStrategy;
		return this;
	}

	public PhysicalNamingStrategy getPhysicalNamingStrategy() {
		return this.physicalNamingStrategy;
	}

	public SpringSpecificationHibernateSettings hibernatePropertiesCustomizers(
			Collection<SpringSpecificationHibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
		this.hibernatePropertiesCustomizers = new ArrayList<>(
				hibernatePropertiesCustomizers);
		return this;
	}

	public Collection<SpringSpecificationHibernatePropertiesCustomizer> getHibernatePropertiesCustomizers() {
		return this.hibernatePropertiesCustomizers;
	}

}
