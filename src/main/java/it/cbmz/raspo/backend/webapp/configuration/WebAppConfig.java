package it.cbmz.raspo.backend.webapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebAppConfig implements WebFluxConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:public/", "classpath:templates/");
	}

/**
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.freeMarker();
	}
	// Configure Freemarker...
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("classpath:/templates");
		return configurer;
	}
**/
}