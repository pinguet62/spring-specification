package fr.pinguet62.springspecification.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * <b>Meta</b>-annotation to define an <b>injectable</b> {@link Rule}.
 */
@Target(TYPE)
@Retention(RUNTIME)
// meta-annotation
@Component
@Scope(SCOPE_PROTOTYPE)
public @interface SpringRule {
}
