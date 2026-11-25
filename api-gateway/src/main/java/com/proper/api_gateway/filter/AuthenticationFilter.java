package com.proper.api_gateway.filter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final String ADMIN = "ADMINISTRADOR";
    private static final String VENDEDOR = "VENDEDOR";
    private static final String CLIENTE = "CLIENTE";

    @Value("${jwt.secret}")
    private String secreto;

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpMethod method = exchange.getRequest().getMethod();
            String path = exchange.getRequest().getURI().getPath();

            if (HttpMethod.OPTIONS.equals(method)) {
                return chain.filter(exchange);
            }

            // Documentación Swagger libre (solo lectura)
            if (HttpMethod.GET.equals(method)
                    && (path.endsWith("/v3/api-docs") || path.contains("/v3/api-docs/"))) {
                return chain.filter(exchange);
            }

            // Catálogo se puede visualizar sin haber iniciado sesión
            if (HttpMethod.GET.equals(method)
                    && (path.startsWith("/api/v1/productos") || path.startsWith("/api/v1/inventario"))) {
                return chain.filter(exchange);
            }

            // Validar cabecera Authorization
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            // Validar firma y expiración, y obtener los claims
            Claims claims;
            try {
                claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            } catch (Exception e) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            // Validar permisos según rol
            List<String> roles = extraerRoles(claims);
            if (!tienePermiso(roles, method, path)) {
                return onError(exchange, HttpStatus.FORBIDDEN);
            }

            return chain.filter(exchange);
        };
    }

    private List<String> extraerRoles(Claims claims) {
        Object raw = claims.get("roles");
        if (raw instanceof List<?> lista) {
            return lista.stream().map(String::valueOf).collect(Collectors.toList());
        }
        return List.of();
    }

    private boolean tienePermiso(List<String> roles, HttpMethod method, String path) {
        // El administrador tiene acceso total
        if (roles.contains(ADMIN)) {
            return true;
        }

        boolean esGet = HttpMethod.GET.equals(method);
        boolean esProductos = path.startsWith("/api/v1/productos");
        boolean esPedidos = path.startsWith("/api/v1/pedido");

        // Vendedor: solo ver productos y pedidos
        if (roles.contains(VENDEDOR) && esGet && (esProductos || esPedidos)) {
            return true;
        }

        // Cliente: solo la tienda
        if (roles.contains(CLIENTE)) {
            if (esGet && esProductos) {
                return true;
            }
            boolean esCrearPedido = HttpMethod.POST.equals(method)
                    && (path.equals("/api/v1/pedido") || path.equals("/api/v1/pedido/"));
            if (esCrearPedido) {
                return true;
            }
        }

        return false;
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
}