package fr.pinguet62.springruleengine.core.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestRules {

    public Rule TRUE_RULE = context -> true;

    public Rule FALSE_RULE = context -> false;

}