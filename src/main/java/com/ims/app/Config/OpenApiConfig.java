package com.ims.app.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Inventory Management")
                        .description("""
                                Oracle Inventory Management System optimizes inventory operations with real-time tracking, automated replenishment, and demand forecasting. It integrates with Oracle ERP and Supply Chain Management for seamless data flow, reducing costs, minimizing stockouts, and enhancing supply chain efficiency.
                                 """)
                        .version("1.0"));
    }
//
//    @Bean
//    public GroupedOpenApi api() {
//        return GroupedOpenApi.builder()
//                .group("api")
//                .packagesToScan("com.ims.app")  // Adjust this to your base package
//                .addOpenApiCustomizer(openApi -> {
//                    openApi.getComponents()
//                            .addSecuritySchemes("bearer-token",
//                                    new SecurityScheme()
//                                            .type(SecurityScheme.Type.HTTP)
//                                            .scheme("bearer")
//                                            .bearerFormat("JWT")
//                            );
//                    openApi.addSecurityItem(
//                            new SecurityRequirement().addList("bearer-token")
//                    );
//                }).build();
//    }
}
