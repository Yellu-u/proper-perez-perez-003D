package com.proper.service_facturacion.config;

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
                .title("API Facturación - Módulo Tributario")
                .version("1.0")
                .description("Documentación del microservicio encargado del procesamiento de cuentas fiscales y cobros."));
    }
}