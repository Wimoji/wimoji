package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
	public LoggingFilter(){
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
				ServerHttpRequest request = exchange.getRequest();
				ServerHttpResponse response = exchange.getResponse();

				log.info("Logging Filter baseMessage: {}",config.getBaseMessage());

				if(config.isPreLogger()){
					log.info("Logging PRE Filter: request id -> {}", request.getId());
				}
				//Custom post filter
				return chain.filter(exchange).then(Mono.fromRunnable(()->{
					if(config.isPostLogger()){
						log.info("Logging POST Filter: response id -> {}", response.getStatusCode());
					}
				}));
		}, Ordered.HIGHEST_PRECEDENCE);

		return filter;

	}

	@Data
	public static class Config{
		//Put the configuration properties
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}

}
