package com.wimoji.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.LastChatRepository;
import com.wimoji.repository.dto.entity.Chat;
import com.wimoji.repository.dto.entity.ChatRoom;
import com.wimoji.repository.dto.request.LastChatReq;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.response.ChatRoomRes;
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
		for(ChatRoomRes chatRoom : chatRooms) {
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

		boolean isNew = false;
		if(chatRoom.getContent().size() != idx) {
			isNew = true;
		}
		ChatRoomRes chatRoomRes = new ChatRoomRes(chatRoom, isNew);

		return chatRoomRes;
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
		int participant = chatRoomRepository.decParticipant(rid);
	}

	/**
	 * 채팅방의 참여 유저 목록에 새로운 유저 추가
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void addUserToList(String rid, String uid) {
		int idx = chatRoomRepository.getLastChat(rid);
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
		return chatRoomRepository.isExistUser(rid);
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 저장
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void makeLastChat(String uid, String rid) {
		try {
			int idx = chatRoomRepository.getLastChat(rid);
			LastChatReq chatReq = new LastChatReq(uid, rid, idx);

			lastChatRepository.save(chatReq);
		} catch (NullPointerException e) {
			return;
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 조회
	 * @param : 채팅방의 id, 유저의 id
	 * @return : 마지막 메시지의 인덱스
	 **/
	public int getLastChat(String uid, String rid) {
		return lastChatRepository.getLastChat(uid, rid);
	}

	/**
	 * 유저가 마지막으로 읽은 메시지로부터 10번 먼저 온 메시지부터 새로운 모든 메시지 출력
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List
	 **/
	public List<ChatRes> getNewChat(NewChatReq newChatReq) {
		try {
			if(newChatReq.getStartIdx() < 10) {
				newChatReq.setStartIdx(0);
			} else {
				newChatReq.setStartIdx(newChatReq.getStartIdx() - 10);
			}

			List<Chat> chatList = chatRoomRepository.getNewChat(newChatReq);
			List<ChatRes> chatResList = new ArrayList<>();

			for(Chat chat : chatList) {
				String flag = (newChatReq.getUid().equals(chat.getUid()))? "1" : "2";
				chatResList.add(new ChatRes(chat.getRid(), chat.getNickname(), chat.getContent(), flag));
			}

			return chatResList;
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}

	/**
	 * 이전 메시지를 30개씩 조회
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List
	 **/
	public List<ChatRes> getPastChat(NewChatReq newChatReq) {
		try {
			if(newChatReq.getStartIdx() < 30) {
				newChatReq.setStartIdx(0);
			} else {
				newChatReq.setStartIdx(newChatReq.getStartIdx() - 30);
			}

			List<Chat> chatList = chatRoomRepository.getPastChat(newChatReq);
			List<ChatRes> chatResList = new ArrayList<>();

			for(Chat chat : chatList) {
				String flag = (newChatReq.getUid().equals(chat.getUid()))? "1" : "2";
				chatResList.add(new ChatRes(chat.getRid(), chat.getNickname(), chat.getContent(), flag));
			}

			return chatResList;
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}
}