package fr.pinguet62.springruleengine.sample;

import fr.pinguet62.springruleengine.server.SpringSpecificationServerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Import(SpringSpecificationServerApplication.class)
@PropertySource("classpath:/application_sample.properties")
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

}