package fr.pinguet62.springspecification.core.builder.database.factory;

import fr.pinguet62.springspecification.core.api.Rule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks a constructor, field or setter method as to be injectable by a sub-{@link Rule}.
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface RuleChild {
}
