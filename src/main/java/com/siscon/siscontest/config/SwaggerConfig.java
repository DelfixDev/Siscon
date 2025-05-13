package com.siscon.siscontest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("API Gestion de Empleados para Siscon")
                .version("V1")
                .description("API restfull para la gestion de empleados de Siscon Test"));
    }
}
