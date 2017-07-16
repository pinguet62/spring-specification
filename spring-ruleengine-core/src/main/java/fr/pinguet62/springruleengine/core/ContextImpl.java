package fr.pinguet62.springruleengine.core;

import java.util.HashMap;
import java.util.Map;

public class ContextImpl extends HashMap<String, Object> implements Context {

    private static final long serialVersionUID = 1;

    public ContextImpl(Map<String, Object> params) {
        putAll(params);
    }

}