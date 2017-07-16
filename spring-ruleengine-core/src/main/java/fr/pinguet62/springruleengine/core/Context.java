package fr.pinguet62.springruleengine.core;

import java.util.Map;

import fr.pinguet62.springruleengine.core.rule.Rule;

/** Parameter for {@link Rule} methods. */
public interface Context extends Map<String, Object> {

    public default <T> T get(String key, Class<T> type) {
        Object value = get(key);
        if (value == null)
            return null;
        if (!type.isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException("Incorrect type specified for key '" + key + "'. Expected [" + type
                    + "] but actual type is [" + value.getClass() + "]");
        return (T) value;
    }

}