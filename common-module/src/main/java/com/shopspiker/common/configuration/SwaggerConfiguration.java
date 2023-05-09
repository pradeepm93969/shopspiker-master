package com.shopspiker.common.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Documentaion", version = "V1.0"))
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme basicScheme = new SecurityScheme()
                .name("basicScheme")
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic");

        SecurityScheme bearerScheme = new SecurityScheme()
                .name("bearerScheme")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("basicAuth", basicScheme)
                        .addSecuritySchemes("bearerAuth", bearerScheme)
                );
    }

    @Bean
    public GroupedOpenApi siteApis() {
        String paths[] = {"/site/**"};
        return GroupedOpenApi.builder().group("Site-Service").pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi authApis() {
        String paths[] = {"/auth/**"};
        return GroupedOpenApi.builder().group("Auth-Service").pathsToMatch(paths)
                .build();
    }



}

