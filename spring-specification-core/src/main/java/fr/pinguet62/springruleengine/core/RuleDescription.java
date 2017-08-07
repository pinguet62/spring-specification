package fr.pinguet62.springruleengine.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Long description used to help user into <i>admin UI</i>. */
@Target(TYPE)
@Retention(RUNTIME)
public @interface RuleDescription {

    String value();

}