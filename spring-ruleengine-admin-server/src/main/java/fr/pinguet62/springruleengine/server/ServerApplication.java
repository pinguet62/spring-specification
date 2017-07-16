package fr.pinguet62.springruleengine.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import fr.pinguet62.springruleengine.core.SpringRuleEngineModule;
import fr.pinguet62.springruleengine.sample.SpringRuleEngineSample;

@Import({ SpringRuleEngineModule.class, SpringRuleEngineSample.class })
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}