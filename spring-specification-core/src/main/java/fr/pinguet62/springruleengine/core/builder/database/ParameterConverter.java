package fr.pinguet62.springruleengine.core.builder.database;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Manages {@link Converter}s used during injection of {@link String} {@link RuleParameter} into typed {@link Rule} implementations.
 */
@Component
public class ParameterConverter {

    /**
     * Converter from {@link String} to any other {@link Class}.
     *
     * @param <T>
     */
    public interface Converter<T> {

        /**
         * The target supported {@link Class}.
         */
        Class<T> getTargetType();

        T convert(String value);

    }

    public static abstract class SimpleConverter<T> implements Converter<T> {

        @Getter
        private final Class<T> targetType;

        public SimpleConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        public abstract T convert(String value);

    }

    @Getter
    private final Collection<Converter<?>> converters = new ArrayList<>();

    /**
     * Register common {@link Converter}s.
     */
    public ParameterConverter() {
        converters.add(new SimpleConverter<String>(String.class) {
            @Override
            public String convert(String value) {
                return value;
            }
        });
        converters.add(new SimpleConverter<Integer>(Integer.class) {
            @Override
            public Integer convert(String value) {
                return Integer.valueOf(value);
            }
        });
        converters.add(new SimpleConverter<Double>(Double.class) {
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