package fr.pinguet62.springruleengine.core;

import lombok.experimental.UtilityClass;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@UtilityClass
public class TestUtils {

    @SafeVarargs
    public static <K, V> Map<K, V> map(Entry<K, V>... entries) {
        Map<K, V> map = new HashMap<>();
        for (Entry<K, V> entry : entries)
            map.put(entry.getKey(), entry.getValue());
        return map;
    }

    public static <K, V> Entry<K, V> entry(K key, V value) {
        return new SimpleEntry<>(key, value);
    }

}