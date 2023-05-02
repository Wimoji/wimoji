package com.wimoji.aop;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionInterceptor implements ChannelInterceptor {
	private final ChatRoomRepository chatRoomRepository;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

		if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
			String rid = headerAccessor.getFirstNativeHeader("rid");
			if (!validateSubscription(rid)) {
				throw new GeneralException(Code.LIMIT_ERROR);
			}
		}

		return message;
	}

	/**
	 * 유효한 채팅방인지 검사
	 * @param : 채팅방의 id
	 * @return : true or false
	 **/
	private boolean validateSubscription(String rid) {
		ChatRoomRes chatRoom = chatRoomRepository.findById(rid);
		if (chatRoom != null) {
			return true;
		}
		return false;
	}
}