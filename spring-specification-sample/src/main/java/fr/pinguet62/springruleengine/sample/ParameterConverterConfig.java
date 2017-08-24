package fr.pinguet62.springruleengine.sample;

import fr.pinguet62.springruleengine.core.builder.database.ParameterConverter;
import fr.pinguet62.springruleengine.core.builder.database.ParameterConverter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Configuration
public class ParameterConverterConfig {

    @Autowired
    private ParameterConverter parameterConverter;

    @PostConstruct
    public void registerCustomerParameterConverters() {
        parameterConverter.getConverters().add(new Converter<BigDecimal>() {
            @Override
            public Class<BigDecimal> getTargetType() {
                return BigDecimal.class;
            }

            @Override
            public BigDecimal convert(String value) {
                return new BigDecimal(value);
            }
        });
    }

}
