package fr.pinguet62.springruleengine.core.api;

import lombok.experimental.UtilityClass;

/**
 * Utilities for {@link Rule} testing.
 */
@UtilityClass
public class TestRules {

    public static Rule<Void> TRUE_RULE = context -> true;

    public static Rule<Void> FALSE_RULE = context -> false;

}