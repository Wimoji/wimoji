package com.wimoji.aop;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.response.ChatRoomRes;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubscriptionInterceptor implements ChannelInterceptor {
	private final MessageChannel messageChannel;
	private final ChatRoomRepository chatRoomRepository;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
			StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
			headerAccessor.setSessionId(accessor.getSessionId());
			headerAccessor.setSubscriptionId(accessor.getSubscriptionId());

			MessageHeaders headers = message.getHeaders();
			MultiValueMap<String, String> map = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);
			String id = map.getFirst("id");
			if(!validateSubscription(id)) {
				headerAccessor.setMessage("FULL");
				message = null;
			} else {
				headerAccessor.setMessage("OK");
			}

			messageChannel.send(MessageBuilder.createMessage(new byte[0], headerAccessor.getMessageHeaders()));
		}
		return message;
	}

	private boolean validateSubscription(String roomId) {
		ChatRoomRes chatRoom = chatRoomRepository.findById(roomId);
		if(chatRoom != null && ChatRoomRes.isLimit(chatRoom)) {
			return true;
		}
		return false;
	}
}