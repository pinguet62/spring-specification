package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.SpringSpecificationModule;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(SpringSpecificationModule.class)
public class SpringSpecificationServerApplication {
}