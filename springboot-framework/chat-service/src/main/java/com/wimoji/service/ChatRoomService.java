package com.wimoji.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.LastChatRepository;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.request.LastChatReq;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.response.ChatRoomRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;
	private final LastChatRepository lastChatRepository;

	/**
	 * id가 일치하는 채팅방을 반환
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 **/
	public ChatRoomRes getRoom(String rid) {
		ChatRoomRes chatRoom = chatRoomRepository.findById(rid);
		return chatRoom;
	}

	/**
	 * 이모지의 정보를 담은 새로운 채팅방을 생성
	 * @param : ChatRoomReq entity
	 * @return :
	 **/
	public void makeRoom(ChatRoomReq chatRoomReq) {
		chatRoomRepository.save(chatRoomReq);
	}

	/**
	 * 기존 data에 새로운 메시지 추가
	 * @param : 채팅방의 id, 채팅을 보낸 사람, 채팅 내용
	 * @return :
	 **/
	public void saveContent(ChatRes chat) {
		chatRoomRepository.saveContent(chat);
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
		chatRoomRepository.addUserToList(rid, uid);
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
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 저장
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void makeLastChat(String uid, String rid) {
		int idx = chatRoomRepository.getLastChat(rid);
		LastChatReq chatReq = new LastChatReq(uid, rid, idx);

		lastChatRepository.save(chatReq);
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
		int idx = (newChatReq.getIdx() < 10)? 0 : (newChatReq.getIdx() - 10);
		newChatReq.setIdx(idx);

		return chatRoomRepository.getNewChat(newChatReq);
	}
}