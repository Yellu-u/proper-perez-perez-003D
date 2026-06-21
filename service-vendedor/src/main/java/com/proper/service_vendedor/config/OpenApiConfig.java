package com.proper.service_vendedor.config;

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
                .title("API Vendedores - Empresas")
                .version("1.0")
                .description("Documentación oficial del microservicio encargado de los datos de la fuerza de venta de la empresa."));
    }
}