package com.wimoji.gatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

// @Configuration
public class FilterConfig {
	// @Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
		return builder.routes()
			.route(r->r.path("/user-service/**")
				.filters(f->f.addRequestHeader("user-request","user-request-header")
							.addResponseHeader("user-response","user-response-header"))
				.uri("http://localhost:8081"))
			.route(r->r.path("/emoji-service/**")
				.filters(f->f.addRequestHeader("emoji-request","emoji-request-header")
					.addResponseHeader("emoji-response","emoji-response-header"))
				.uri("http://localhost:8082"))
			.route(r->r.path("/chat-service/**")
				.filters(f->f.addRequestHeader("chat-request","chat-request-header")
					.addResponseHeader("chat-response","chat-response-header"))
				.uri("http://localhost:8083"))
			.build();
	}
}
