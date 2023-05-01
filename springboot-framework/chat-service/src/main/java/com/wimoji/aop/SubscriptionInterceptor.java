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
		if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
			String userId = headerAccessor.getFirstNativeHeader("userId");
			String roomId = headerAccessor.getFirstNativeHeader("roomId");
			if(userId == null || roomId == null) {
				throw new GeneralException(Code.BAD_REQUEST);
			}

			if(!isExist(userId, roomId)) {
				if (!validateSubscription(roomId)) {
					throw new GeneralException(Code.LIMIT_ERROR);
				}

				chatRoomRepository.incParticipant(roomId);
				chatRoomRepository.addUserToList(roomId, userId);
			}
		}
		return message;
	}

	/**
	 * 특정 유저가 특정 채팅방에 존재하는지 확인
	 * @param : 유저의 id, 채팅방의 id
	 * @return : true or false
	 **/
	private boolean isExist(String uid, String rid) {
		List<String> userList = chatRoomRepository.isExistUser(rid);
		for(String userId : userList) {
			if(userId.equals(uid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 유효한 채팅방인지 검사
	 * @param : 채팅방의 id
	 * @return : true or false
	 **/
	private boolean validateSubscription(String rid) {
		ChatRoomRes chatRoom = chatRoomRepository.findById(rid);
		if (chatRoom != null && ChatRoomRes.isLimit(chatRoom)) {
			return true;
		}
		return false;
	}
}