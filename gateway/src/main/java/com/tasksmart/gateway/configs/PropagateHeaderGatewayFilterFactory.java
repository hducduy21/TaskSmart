package com.tasksmart.gateway.configs;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class PropagateHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String authorizationHeader = request.getHeaders().getFirst("Authorization");

            if (authorizationHeader != null) {
                request.mutate().header("Authorization", authorizationHeader).build();
            }

            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}