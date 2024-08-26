package com.tasksmart.gateway.configs;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * Gateway filter factory to propagate the Authorization header to the downstream services.
 *
 * @author Duy Hoang
 */
@Component
public class PropagateHeaderGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    /**
     * Apply the filter to the request.
     *
     * @param config the configuration object
     * @return the gateway filter
     */
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