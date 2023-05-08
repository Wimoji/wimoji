package com.wimoji.aop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.response.UserRes;
import com.wimoji.service.UserServiceClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionInterceptor implements ChannelInterceptor {
	private final Map<String, String> sessionIdMap = new ConcurrentHashMap<>();
	private final UserServiceClient userServiceClient;
	private final ObjectMapper mapper;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		String sessionId = headerAccessor.getSessionId();

		if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
			String userId = getUidByToken(headerAccessor.getFirstNativeHeader("Authorization"));
			sessionIdMap.put(userId, sessionId);
			// headerAccessor.setHeader("session", sessionId);
		} else if (headerAccessor.getCommand().equals(StompCommand.DISCONNECT)) {
			sessionIdMap.remove(sessionId);
		}

		return message;
	}

	/**
	 * sessionId와 userId가 저장된 map을 반환
	 * @param :
	 * @return : sessionIdMap
	 **/
	public Map<String, String> getSessionMap() {
		return sessionIdMap;
	}

	/**
	 * 토큰에서 사용자 정보 추출
	 * @param : accessToken
	 * @return : user Id와 name
	 **/
	private String getUidByToken(String accessToken) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(accessToken), UserRes.class);

			return user.getUid();
		} catch (JsonMappingException e) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (JsonProcessingException e) {
			throw new GeneralException(Code.UNAUTHORIZED);
		}
	}
}