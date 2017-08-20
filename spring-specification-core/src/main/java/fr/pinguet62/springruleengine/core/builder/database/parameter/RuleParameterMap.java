package fr.pinguet62.springruleengine.core.builder.database.parameter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Set {@code Map<String,Object>} to property.
 */
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface RuleParameterMap {
}
