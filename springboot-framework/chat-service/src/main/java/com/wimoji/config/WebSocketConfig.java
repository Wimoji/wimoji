package com.wimoji.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// client가 webSocket handshake connection을 생성할 경로 설정
		registry.addEndpoint("/ws/chat")
			.setAllowedOriginPatterns("*")
			.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO: enableStompBrokerRelay를 통해 외부 메세지 브로커를 연동하고 /topic, /queue로 변경
		// put으로 시작하는 destination header는 @MessageMapping으로 라우팅
		registry.setApplicationDestinationPrefixes("/pub");
		// 클라이언트가 메시지를 구독할 endpoint 정의
		registry.enableSimpleBroker("/sub");
	}
}