package com.wimoji.gatewayservice.filter;

import java.util.Iterator;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

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

			if(config.isPreLogger()){
				log.info("request path : {}", request.getPath());
				MultiValueMap<String,String> params = request.getQueryParams();
				Iterator<String> iter = params.keySet().iterator();
				while(iter.hasNext()){
					String key = iter.next();
					for(int i=0;i<params.get(key).size();i++){
						String value = params.get(key).get(i);
						log.info("request query params : {} {}", key,value);
					}
				}
			}
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				if(config.isPostLogger()){
					log.info("response status : {}", response.getStatusCode());
				}
			}));
		};
	}

	@Data
	public static class Config{
		//Put the configuration properties
		private boolean preLogger;
		private boolean postLogger;
	}

}
