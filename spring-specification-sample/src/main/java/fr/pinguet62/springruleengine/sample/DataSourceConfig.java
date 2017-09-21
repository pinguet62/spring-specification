package fr.pinguet62.springruleengine.sample;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * Because <i>Spring Specification</i> define a {@link DataSource}, this application must define his {@link Primary} {@link Bean}.
 */
@Configuration
@EnableJpaRepositories
@EntityScan
public class DataSourceConfig {
}
