package fr.pinguet62.springspecification.core.builder.database.parameter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Value("any value for compilation")
public @interface RuleParameter {

    /**
     * Parameter <b>key</b>, used into database.<br>
     * Default: property name.
     */
    @AliasFor(annotation = Value.class, attribute = "value")
    String value();

}