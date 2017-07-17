package fr.pinguet62.springruleengine.core.sample;

import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.ContextImpl;
import fr.pinguet62.springruleengine.core.sample.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    public Context sampleContext() {
        Map<String, Object> params = new HashMap<>();
        params.put("product", Product.builder().name("Beer").color("black").price(450.00).build());
        params.put("date", new Date());
        return new ContextImpl(params);
    }

    @SafeVarargs
    public <K, V> Map<K, V> map(Entry<K, V>... entries) {
        Map<K, V> map = new HashMap<>();
        for (Entry<K, V> entry : entries)
            map.put(entry.getKey(), entry.getValue());
        return map;
    }

    public <K, V> Entry<K, V> entry(K key, V value) {
        return new SimpleEntry<>(key, value);
    }

}