package com.wimoji.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.ChatRepository;
import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.request.ChatReq;
import com.wimoji.repository.dto.request.InOutChatReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
	// message broker 사용 template
	private final SimpMessagingTemplate template;
	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;

	/**
	 * 채팅방 참여 시 알림
	 * @param : 채팅 정보를 담은 ChatReq
	 * @return 참여한 사람의 정보를 알림
	 * **/
	// @MessageMapping("/chat/enter")
	// public void enter(InOutChatReq enterChat) {
	// 	ChatReq chat = new ChatReq(enterChat.getRid(),
	// 		enterChat.getUserId(),
	// 		enterChat.getUserName() + "님이 채팅방에 참여하였습니다.");
	// 	template.convertAndSend("/sub/chat/" + chat.getRid(), chat);
	//
	// 	chatRoomRepository.incParticipant(enterChat.getRid());
	// 	chatRoomRepository.addUserToList(enterChat.getRid(), enterChat.getUserId());
	// }

	/**
	 * 사용자간 채팅
	 * @param : 채팅 정보를 담은 ChatReq
	 * @return 채팅을 보낸 채팅방에 대해서만 새로운 채팅 반환
	 * **/
	@MessageMapping("/chat/message")
	public void chat(ChatReq chat) {
		template.convertAndSend("/sub/chat/" + chat.getRid(), chat);
		try {
			chatRepository.save(chat);
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
		// 이거 돌려줄 때 Res로 바꿔서 id도 넣어서 돌려줘야 함 그러면 프론트에서 그거 저장하기
	}

	@MessageMapping("/chat/exit")
	public void chat(InOutChatReq outChat) {
		ChatReq chat = new ChatReq(outChat.getRid(),
			outChat.getUserId(),
			outChat.getUserName() + "님이 채팅방에서 퇴장하였습니다.");
		template.convertAndSend("/sub/chat/" + chat.getRid(), chat);

		chatRoomRepository.decParticipant(outChat.getRid());
		chatRoomRepository.deleteUserToList(outChat.getRid(), outChat.getUserId());
	}
}