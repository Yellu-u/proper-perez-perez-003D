package com.proper.service_reporte.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig 
{

    @Bean
    public OpenAPI customOpenAPI() 
    {
        return new OpenAPI()
                .info(new Info()
                .title("API Reportes - Business Intelligence")
                .version("1.0")
                .description("Documentación del microservicio encargado de centralizar la analítica corporativa, métricas de rendimiento y KPIs gerenciales."));
    }
}