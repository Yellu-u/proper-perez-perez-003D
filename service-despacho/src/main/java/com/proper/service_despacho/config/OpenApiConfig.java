package com.proper.service_despacho.config;

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
                .title("API Despacho - Logística y Distribución")
                .version("1.0")
                .description("Documentación del microservicio encargado de las rutas de entrega, despachos y estados de transportes."));
    }
}