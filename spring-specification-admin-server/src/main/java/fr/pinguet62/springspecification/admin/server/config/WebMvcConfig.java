package fr.pinguet62.springspecification.admin.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configure <b>Spring Boot</b> embedded Angular website.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private static final String CONTEXT = "/spring-specification-admin-client";

    /**
     * Handle resources.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler(CONTEXT + "/**")
                .addResourceLocations("classpath:/META-INF/spring-specification-admin-client/")
                .resourceChain(true);
    }

    /**
     * Define root URL.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);

        registry.addViewController(CONTEXT + "/").setViewName("forward:index.html");
    }
}