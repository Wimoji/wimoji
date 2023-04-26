package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
	public CustomFilter(){
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		//Custom pre filter
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();
			log.info("Custom PRE filter : request id -> {}",request.getId());

			//Custom post filter
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				log.info("Custom POST filter : response code -> {}",response.getStatusCode());
			}));
		};
	}

	public static class Config{
		//Put the configuration properties
	}

}
