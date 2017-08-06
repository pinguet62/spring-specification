package fr.pinguet62.springruleengine.core.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestRules {

    public static Rule TRUE_RULE = context -> true;

    public static Rule FALSE_RULE = context -> false;

}