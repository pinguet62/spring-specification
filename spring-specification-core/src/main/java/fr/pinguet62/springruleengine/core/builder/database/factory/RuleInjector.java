package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.Rule;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class RuleInjector {

    /**
     * Because the injected value depends on a execution context, so must support multithreading.
     */
    public static ThreadLocal<List<Rule<?>>> CONTEXT = new ThreadLocal<>();

}
