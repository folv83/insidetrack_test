package com.insidetrackdata.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI setup() {
		Info info = new Info()
				.title("TEST")
				.description("Test for InsideTrack")
				.version("1.0.0")
				.license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
				.contact(new Contact().name("Félix Lira").email("folv83@gmail.com"));
		
		return new OpenAPI()
				.info(info);
	}

}
