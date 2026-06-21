package com.proper.api_gateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{
    @Value("${jwt.secret}")
    private String secreto;
    public AuthenticationFilter()
    {
        super(Config.class);
    }
    public static class Config
    {
        // Config adicional si fuese necesaria para en un futuro
    }

    @Override
    public GatewayFilter apply(Config config) 
    {
        return (exchange, chain) -> 
        {
            // Obtener la cabecera Authorization
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // Validar que exista y tenga el formato "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) 
            {
                return onError(exchange, "Token faltante o formato inválido", HttpStatus.UNAUTHORIZED);
            }

            // Extraer el token (quitando "Bearer ")
            String token = authHeader.substring(7);

            try 
            {
                // Validar el token con la firma
                Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
                
            } catch (Exception e) {
                // Si el token expiró o la firma no coincide
                return onError(exchange, "Token inválido o expirado", HttpStatus.UNAUTHORIZED);
            }
             // Si todo está bien, continuar al microservicio
            return chain.filter(exchange);
        };
    }
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) 
    {
        exchange.getResponse().setStatusCode(httpStatus);
        // Aquí podrías añadir lógica para escribir el mensaje 'err' en el body si quisieras
        return exchange.getResponse().setComplete();
    }
}