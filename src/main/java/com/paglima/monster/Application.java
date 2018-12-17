package com.paglima.monster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paglima.monster.configuration.application.condition.DevelopmentEnvironmentCondition;
import com.paglima.monster.configuration.application.condition.NotDevelopmentEnvironmentCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ComponentScan({"com.paglima"})
@PropertySource(value = "classpath:application.properties")
@Slf4j
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	@Conditional(DevelopmentEnvironmentCondition.class)
	@PropertySource(value = "classpath:application.properties")
	@PropertySource(value = "file://${appconfig.root}/development/application.properties", ignoreResourceNotFound = true)
	@PropertySource(value = "file://${appconfig.root}/development/${spring.application.name}/application.properties", ignoreResourceNotFound = true)
	@PropertySource(value = "file://${appconfig.root}/development/${spring.application.name}/${app.version}/application.properties", ignoreResourceNotFound = true)
	static class DevelopmentEnvironmentConfiguration {
	}

	@Configuration
	@Conditional(NotDevelopmentEnvironmentCondition.class)
	@PropertySource(value = "classpath:application.properties")
	@PropertySource(value = "file://${appconfig.root}/${APP_ENVIRONMENT}/application.properties", ignoreResourceNotFound = true)
	@PropertySource(value = "file://${appconfig.root}/${APP_ENVIRONMENT}/${spring.application.name}/application.properties", ignoreResourceNotFound = true)
	@PropertySource(value = "file://${appconfig.root}/${APP_ENVIRONMENT}/${spring.application.name}/${app.version}/application.properties", ignoreResourceNotFound = true)
	static class NotDevelopmentEnvironmentConfiguration {
	}

	@Autowired
	private Environment environment;

	@PostConstruct
	public void init() throws JsonProcessingException {

		Map<String, Object> properties = getAllKnownProperties(environment);
		String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(properties);

		log.info(json);
	}

	private static Map<String, Object> getAllKnownProperties(Environment env) {

		Map<String, Object> rtn = new HashMap<>();
		if (env instanceof ConfigurableEnvironment) {
			for (org.springframework.core.env.PropertySource propertySource : ((ConfigurableEnvironment) env).getPropertySources()) {
				if (propertySource instanceof EnumerablePropertySource) {

					log.debug(propertySource.getName());

					for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
						rtn.put(key, propertySource.getProperty(key));
					}
				}
			}
		}
		return rtn;
	}

}

