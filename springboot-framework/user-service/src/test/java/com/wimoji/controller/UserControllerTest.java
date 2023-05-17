package com.wimoji.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.wimoji.repository.dto.request.UserReq;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	private static Map<String, String> tokenStorage = new HashMap<>();

	@Test
	@DisplayName("회원가입 테스트")
	@Order(1)
	void makeUser() throws Exception {
		// 유효한 입력
		UserReq validUserReq = new UserReq();
		validUserReq.setUid("test");
		validUserReq.setNickname("test");
		validUserReq.setPassword("test1234");

		String validJson = mapper.writeValueAsString(validUserReq);
		mockMvc.perform(post("/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(validJson))
			.andExpect(status().isOk());

		// 중복된 아이디로 회원가입 시도
		UserReq duplicateUserReq = new UserReq();
		duplicateUserReq.setUid("test");
		duplicateUserReq.setNickname("test");
		duplicateUserReq.setPassword("test1234");

		String duplicateJson = mapper.writeValueAsString(duplicateUserReq);
		mockMvc.perform(post("/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(duplicateJson))
			.andExpect(status().isConflict()); // 중복된 아이디로 인한 BadRequest 예상
	}

	@Test
	@DisplayName("로그인 테스트")
	@Order(2)
	void setLoginUser() throws Exception {
		// 유효한 입력
		UserReq validUserReq = new UserReq();
		validUserReq.setUid("test");
		validUserReq.setPassword("test1234");

		MvcResult result = mockMvc.perform(put("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(validUserReq)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").exists())
			.andReturn();

		// 토큰 저장
		String response = result.getResponse().getContentAsString();
		String accessToken = JsonPath.read(response, "$.data.accessToken");
		tokenStorage.put("accessToken", accessToken);

		// 잘못된 입력
		UserReq invalidUserReq = new UserReq();
		invalidUserReq.setUid("test");
		invalidUserReq.setPassword("wrongpassword");

		mockMvc.perform(put("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(invalidUserReq)))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("로그아웃 테스트")
	@Order(3)
	void setLogoutUser() throws Exception {
		String validToken = tokenStorage.get("accessToken");
		String invalidToken = "token";

		mockMvc.perform(put("/logout")
				.header("Authorization", "Bearer " + validToken))
			.andExpect(status().isOk());

		mockMvc.perform(put("/logout")
				.header("Authorization", "Bearer " + invalidToken))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("회원 삭제 테스트")
	@Order(10)
	void removeUser() throws Exception {
		String validToken = tokenStorage.get("accessToken");
		String invalidToken = "token";

		mockMvc.perform(delete("/")
				.header("Authorization", "Bearer " + validToken))
			.andExpect(status().isOk());

		mockMvc.perform(delete("/")
				.header("Authorization", "Bearer " + invalidToken))
			.andExpect(status().isUnauthorized());
	}

	@Test
	@DisplayName("회원 조회 테스트")
	@Order(4)
	void getUser() throws Exception {
		String validToken = tokenStorage.get("accessToken");
		String invalidToken = "token";

		mockMvc.perform(get("/userinfo")
				.param("bearerToken", "Bearer " + validToken))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.uid").value("test"))
			.andExpect(jsonPath("$.nickname").value("test"));

		mockMvc.perform(get("/userinfo")
				.param("bearerToken", invalidToken))
			.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("유저 참여 채팅방 조회")
	@Order(6)
	void getMyChatRoom() throws Exception {
		String token = tokenStorage.get("accessToken");

		// 유효한 입력
		mockMvc.perform(get("/chat")
				.header("Authorization", "Bearer " + token))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").exists());

		// data가 null로 올 경우
	}

	@Test
	@DisplayName("유저 참여 채팅방 생성")
	@Order(5)
	void makeChat() throws Exception {
		String validToken = tokenStorage.get("accessToken");
		String invalidToken = "token";
		String rid = "64647abfd8a5212716147c62"; // DB에 존재하는 rid

		// 올바른 요청
		mockMvc.perform(put("/chat/{rid}", rid)
				.header("Authorization", "Bearer " + validToken))
			.andExpect(status().isOk());

		// 잘못된 요청
		mockMvc.perform(put("/chat/{rid}", rid)
				.header("Authorization", "Bearer " + invalidToken))
			.andExpect(status().isNotFound());

		// 인원이 모두 찬 채팅방에 들어갈 경우
	}

	@Test
	@DisplayName("유저 참여 채팅방 삭제")
	@Order(7)
	void removeChat() throws Exception {
		String validToken = tokenStorage.get("accessToken");
		String invalidToken = "token";
		String validRid = "64647abfd8a5212716147c62"; // DB에 존재하는 rid
		String invalidRid = "12341234"; // DB에 존재하는 rid

		// 올바른 요청
		mockMvc.perform(delete("/chat/{rid}", validRid)
				.header("Authorization", "Bearer " + validToken))
			.andExpect(status().isOk());

		// 잘못된 uid
		mockMvc.perform(delete("/chat/{rid}", validRid)
				.header("Authorization", "Bearer " + invalidToken))
			.andExpect(status().isUnauthorized());

		// 잘못된 rid
		mockMvc.perform(delete("/chat/{rid}", invalidRid)
				.header("Authorization", "Bearer " + validToken))
			.andExpect(status().isNotFound());
	}
}