package fr.pinguet62.springruleengine.core;

import java.util.ArrayList;
import java.util.Collection;

import fr.pinguet62.springruleengine.core.rule.Rule;

public class RuleEngine {

    private final Collection<Rule> rules = new ArrayList<>();

    public void registerRule(Rule rule) {
        rules.add(rule);
    }

    public boolean compute(Context context) {
        return false;
    }

}