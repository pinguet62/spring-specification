package fr.pinguet62.springspecification.core;

import fr.pinguet62.springspecification.core.api.Rule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Short name of {@link Rule}.<br>
 * Default: {@link Class#getSimpleName()}.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface RuleName {

    String value();

}