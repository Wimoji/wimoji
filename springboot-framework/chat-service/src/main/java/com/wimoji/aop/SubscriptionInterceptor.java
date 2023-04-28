package com.wimoji.aop;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.response.ChatRoomRes;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionInterceptor implements ChannelInterceptor {
	private final ChatRoomRepository chatRoomRepository;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
		if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
			String destination = headerAccessor.getDestination();
			if (destination != null && destination.startsWith("/sub/")) {
				String roomId = destination.substring(10);
				String userId = (String) headerAccessor.getSessionAttributes().get("userId");

				if(isExist(userId, roomId)) {
					return message;
				}

				if (!validateSubscription(roomId)) {
					throw new GeneralException(Code.LIMIT_ERROR);
				}
			}
		}
		return message;
	}

	private boolean isExist(String uid, String rid) {
		List<String> userList = chatRoomRepository.isExistUser(rid);
		for(String userId : userList) {
			if(uid.equals(userId)) {
				return true;
			}
		}
		return false;
	}

	private boolean validateSubscription(String roomId) {
		ChatRoomRes chatRoom = chatRoomRepository.findById(roomId);
		if (chatRoom != null && ChatRoomRes.isLimit(chatRoom)) {
			return true;
		}
		return false;
	}
}