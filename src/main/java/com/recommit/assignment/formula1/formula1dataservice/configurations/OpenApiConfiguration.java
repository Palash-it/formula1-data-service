package com.recommit.assignment.formula1.formula1dataservice.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This Class is used to configure Swagger OpenApi to make authorization with Bearer tokens possible through Swagger-ui.
 */

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customizeOpenApi() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info().title("Formula1 API Documentation").description("Its a assignment project")
                        .version("V1.0.0").contact(
                                new Contact().name("Palash")
                                        .email("palash.debnath@recommit.se")
                                        .url("https://www.recommit.se")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
