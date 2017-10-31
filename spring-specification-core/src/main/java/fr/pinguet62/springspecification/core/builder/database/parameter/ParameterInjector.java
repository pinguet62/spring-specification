package fr.pinguet62.springspecification.core.builder.database.parameter;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ParameterInjector {

    /**
     * Because the injected value depends on a execution context, so must support multithreading.
     */
    public static ThreadLocal<Map<String, String>> CONTEXT = new ThreadLocal<>();

}
