package com.wimoji.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.LastChatRepository;
import com.wimoji.repository.dto.entity.Chat;
import com.wimoji.repository.dto.entity.ChatRoom;
import com.wimoji.repository.dto.entity.LastChat;
import com.wimoji.repository.dto.entity.LastChatId;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.repository.dto.response.NumberRes;
import com.wimoji.repository.dto.response.UserEnterRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;
	private final LastChatRepository lastChatRepository;

	public List<ChatRoomRes> getAllRoom() {
		List<ChatRoomRes> chatRooms = chatRoomRepository.findAll();

		// 24글자 대신 8글자를 rid로 사용
		for (ChatRoomRes chatRoom : chatRooms) {
			chatRoom.setRid(chatRoom.getId().substring(0, 8));
		}

		return chatRooms;
	}

	/**
	 * id가 일치하는 채팅방을 반환
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 **/
	public ChatRoomRes getRoom(String rid, int idx) {
		ChatRoom chatRoom = chatRoomRepository.findById(rid);
		if(chatRoom == null) {
			throw new GeneralException(Code.NOT_FOUND);
		}

		boolean isNew = false;
		if (chatRoom.getContent().size()-1 != idx && idx != 0) {
			isNew = true;
		}

		return new ChatRoomRes(chatRoom, isNew);
	}

	/**
	 * 이모지의 정보를 담은 새로운 채팅방을 생성
	 * @param : ChatRoom entity
	 * @return :
	 **/
	public String makeRoom(ChatRoom chatRoom) {
		try {
			return chatRoomRepository.save(chatRoom).getId();
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}

	/**
	 * 채팅방의 최대 인원과 현재 인원을 반환
	 * @param : 채팅방의 id
	 * @return : 최대 인원, 현재 인원
	 **/
	public NumberRes getNumber(@PathVariable String rid) {
		ChatRoom chatRoom = chatRoomRepository.findById(rid);
		if(chatRoom == null) {
			throw new GeneralException(Code.NOT_FOUND);
		}

		boolean isEnter = (chatRoom.getParticipant() == chatRoom.getLimit()) ? false : true;
		return new NumberRes(chatRoom.getParticipant(), chatRoom.getLimit(), isEnter);
	}

	/**
	 * 기존 data에 새로운 메시지 추가
	 * @param : 채팅방의 id, 채팅을 보낸 사람, 채팅 내용
	 * @return :
	 **/
	public void saveContent(Chat chat) {
		try {
			chatRoomRepository.saveContent(chat);
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}

	/**
	 * 채팅방에 참여 인원을 1 증가
	 * @param : 채팅방의 id
	 * @return :
	 **/
	public void incParticipant(String rid) {
		chatRoomRepository.incParticipant(rid);
	}

	/**
	 * 채팅방에 참여 인원을 1 감소
	 * @param : 채팅방의 id
	 * @return :
	 **/
	public void decParticipant(String rid) {
		chatRoomRepository.decParticipant(rid);
	}

	/**
	 * 채팅방의 참여 유저 목록에 새로운 유저 추가
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void addUserToList(String rid, String uid) {
		ChatRoom chatRoom = chatRoomRepository.findById(rid);
		if(chatRoom == null) {
			throw new GeneralException(Code.NOT_FOUND);
		}

		int idx = chatRoom.getContent().size();
		UserEnterRes userEnterRes = new UserEnterRes(uid, idx);
		chatRoomRepository.addUserToList(rid, userEnterRes);
	}

	/**
	 * 채팅방의 참여 유저 목록에서 기존 유저 제거
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void deleteUserToList(String rid, String uid) {
		chatRoomRepository.deleteUserToList(rid, uid);
	}

	/**
	 * 채팅방에 참여한 유저 id 목록 반환
	 * @param : 채팅방의 id
	 * @return : 유저의 id를 담은 List
	 **/
	public List<UserEnterRes> isExistUser(String rid) {
		ChatRoom chatRoom = chatRoomRepository.isExistUser(rid);
		if(chatRoom == null) {
			throw new GeneralException(Code.NOT_FOUND);
		}

		return chatRoom.getUserList();
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 저장
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void makeLastChat(String uid, String rid) {
		ChatRoom chatRoom = chatRoomRepository.findById(rid);
		if(chatRoom == null) {
			throw new GeneralException(Code.NOT_FOUND);
		}

		int idx = chatRoom.getContent().size();
		LastChat chatReq = new LastChat(new LastChatId(uid, rid), idx);

		lastChatRepository.save(chatReq);
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 조회
	 * @param : 채팅방의 id, 유저의 id
	 * @return : 마지막 메시지의 인덱스
	 **/
	public int getLastChat(String uid, String rid) {
		return lastChatRepository.getLastChat(new LastChatId(uid, rid));
	}

	/**
	 * 퇴장 시 마지막으로 읽은 메시지 삭제
	 * @param : 채팅방의 id, accessToken
	 * @return :
	 **/
	public void removeLastChat(String uid, String rid) {
		lastChatRepository.removeLastChat(new LastChatId(uid, rid));
	}

	/**
	 * 유저가 마지막으로 읽은 메시지로부터 15번 먼저 온 메시지부터 새로운 모든 메시지 출력
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List
	 **/
	public Map<String, List> getNewChat(NewChatReq newChatReq, int enterIdx) {
		try {
			int preIdx = 15;
			Map<String, List> result = new HashMap<>();

			List<Integer> firstIdx = new ArrayList<>();
			newChatReq.setStartIdx(setIdx(newChatReq.getStartIdx(), preIdx, enterIdx));
			firstIdx.add(newChatReq.getStartIdx());
			result.put("firstIdx", firstIdx);

			List<Chat> chatList = chatRoomRepository.getNewChat(newChatReq);
			List<ChatRes> chatResList = chatToChatRes(chatList, newChatReq.getUid());
			result.put("chatList", chatResList);

			return result;
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}

	/**
	 * 이전 메시지를 30개씩 조회
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List
	 **/
	public Map<String, List> getPastChat(NewChatReq newChatReq, int enterIdx) {
		try {
			int preIdx = 30;
			Map<String, List> result = new HashMap<>();

			List<Integer> firstIdx = new ArrayList<>();
			newChatReq.setStartIdx(setIdx(newChatReq.getStartIdx(), preIdx, enterIdx));
			firstIdx.add(newChatReq.getStartIdx());
			result.put("firstIdx", firstIdx);

			List<Chat> chatList = chatRoomRepository.getPastChat(newChatReq);
			List<ChatRes> chatResList = chatToChatRes(chatList, newChatReq.getUid());
			result.put("chatList", chatResList);

			return result;
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}

	private List<ChatRes> chatToChatRes(List<Chat> chatList, String uid) {
		List<ChatRes> chatResList = new ArrayList<>();
		String flag;

		for (Chat chat : chatList) {
			if(chat.getUid().equals(uid)) {
				flag = "1";
			} else if(chat.getUid().equals("coment")) {
				flag = "3";
			} else {
				flag = "2";
			}

			chatResList.add(new ChatRes(chat.getRid(), chat.getNickname(), chat.getContent(), flag, chat.getMTime()));
		}

		return chatResList;
	}

	private int setIdx(int nowIdx, int targetIdx, int enterIdx) {
		int result = (nowIdx < targetIdx) ? 0 : (nowIdx - targetIdx);

		if(result < enterIdx) {
			result = enterIdx;
		}

		return result;
	}
}