package com.proper.service_productos.config;

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
                .title("API Catálogo - Servicio de Productos")
                .version("1.0")
                .description("Documentación oficial del microservicio encargado de los Productos y Categorías Comerciales."));
    }
}