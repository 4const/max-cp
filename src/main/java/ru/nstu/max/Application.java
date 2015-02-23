package ru.nstu.max;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.nstu.max.config.WebApplicationConfiguration;
import ru.nstu.max.config.WebSecurityConfiguration;

@SpringBootApplication
@EntityScan("ru.nstu.nd.dance.model")
@EnableJpaRepositories
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		configureApplication(new SpringApplicationBuilder()).run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}

	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder
			.showBanner(false)
			.sources(Application.class,
				WebApplicationConfiguration.class,
				WebSecurityConfiguration.class);
	}
}