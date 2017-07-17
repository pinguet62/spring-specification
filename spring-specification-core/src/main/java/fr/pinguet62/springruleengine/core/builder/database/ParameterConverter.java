package fr.pinguet62.springruleengine.core.builder.database;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class ParameterConverter {

    public static abstract class Converter<T> {

        @Getter
        private final Class<T> targetType;

        public Converter(Class<T> targetType) {
            this.targetType = targetType;
        }

        public abstract T convert(String value);

    }

    @Getter
    private final Collection<Converter<?>> converters = new ArrayList<>();

    public ParameterConverter() {
        converters.add(new Converter<String>(String.class) {
            @Override
            public String convert(String value) {
                return value;
            }
        });
        converters.add(new Converter<Integer>(Integer.class) {
            @Override
            public Integer convert(String value) {
                return Integer.valueOf(value);
            }
        });
        converters.add(new Converter<Double>(Double.class) {
            @Override
            public Double convert(String value) {
                return Double.valueOf(value);
            }
        });
    }

    public Object convert(String value, Class<?> targetType) {
        return converters.stream().filter(c -> c.getTargetType().equals(targetType)).map(c -> c.convert(value)).findFirst()
                .orElseThrow(() -> new UnsupportedOperationException(
                        "No " + ParameterConverter.class.getSimpleName() + " found for type " + targetType.getName()));
    }

}