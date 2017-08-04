package fr.pinguet62.springruleengine.core;

import fr.pinguet62.springruleengine.core.api.Rule;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Short name of {@link Rule}. */
@Target(TYPE)
@Retention(RUNTIME)
public @interface RuleName {

    String value();

}