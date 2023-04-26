package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
	public GlobalFilter(){
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Global Filter baseMessage: {}",config.getBaseMessage());

			if(config.isPreLogger()){
				log.info("Global Filter Start: request id -> {}", request.getId());
			}
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				if(config.isPostLogger()){
					log.info("Global Filter End: response id -> {}", response.getStatusCode());
				}
			}));
		};
	}

	@Data
	public static class Config{
		//Put the configuration properties
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}

}
