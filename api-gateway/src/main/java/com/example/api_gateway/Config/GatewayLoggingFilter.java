package com.example.api_gateway.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Filtro global aplicado a TODAS las rutas del Gateway.
 * Deja trazabilidad de cada solicitud entrante y su respuesta (metodo, ruta, status, tiempo).
 */
@Component
@Slf4j
public class GatewayLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        long inicio = System.currentTimeMillis();

        log.info("--> {} {} (routeando via Gateway)", request.getMethod(), request.getURI());

        return chain.filter(exchange).doFinally(signal -> {
            long duracionMs = System.currentTimeMillis() - inicio;
            int status = exchange.getResponse().getStatusCode() != null
                    ? exchange.getResponse().getStatusCode().value()
                    : 0;
            log.info("<-- {} {} status={} ({} ms)", request.getMethod(), request.getURI(), status, duracionMs);
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
