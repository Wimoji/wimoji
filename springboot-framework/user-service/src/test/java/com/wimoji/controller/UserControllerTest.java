// package com.wimoji.controller;
//
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import javax.management.Query;
//
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.mongodb.core.MongoTemplate;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.event.annotation.AfterTestClass;
// import org.springframework.test.web.servlet.MockMvc;
//
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.wimoji.repository.dto.request.UserReq;
//
// @SpringBootTest
// @AutoConfigureMockMvc
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// class UserControllerTest {
// 	@Autowired
// 	private MockMvc mockMvc;
// 	@Autowired
// 	ObjectMapper mapper;
// 	// @MockBean
// 	// static private MongoTemplate mongoTemplate;
//
// 	// @AfterAll
// 	// static void tearDownAll() {
// 	// 	// 테스트 데이터베이스 초기화
// 	// 	mongoTemplate.remove(new Query(), "user");
// 	// }
//
// 	@Test
// 	@DisplayName("회원가입 테스트")
// 	@Order(1)
// 	void makeUser() throws Exception {
// 		// 유효한 입력
// 		UserReq validUserReq = new UserReq();
// 		validUserReq.setUid("test");
// 		validUserReq.setNickname("test");
// 		validUserReq.setPassword("test1234");
//
// 		String validJson = mapper.writeValueAsString(validUserReq);
// 		mockMvc.perform(post("/signup")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(validJson))
// 			.andExpect(status().isOk());
//
// 		// 중복된 아이디로 회원가입 시도
// 		UserReq duplicateUserReq = new UserReq();
// 		duplicateUserReq.setUid("test");
// 		duplicateUserReq.setNickname("test");
// 		duplicateUserReq.setPassword("test1234");
//
// 		String duplicateJson = mapper.writeValueAsString(duplicateUserReq);
// 		mockMvc.perform(post("/signup")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(duplicateJson))
// 			.andExpect(status().isConflict()); // 중복된 아이디로 인한 BadRequest 예상
// 	}
//
// 	@Test
// 	@DisplayName("로그인 테스트")
// 	@Order(2)
// 	void setLoginUser() throws Exception {
// 		// 유효한 입력
// 		UserReq validUserReq = new UserReq();
// 		validUserReq.setUid("test");
// 		validUserReq.setPassword("test1234");
//
// 		mockMvc.perform(put("/login")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(mapper.writeValueAsString(validUserReq)))
// 			.andExpect(status().isOk())
// 			.andExpect(jsonPath("$.data").exists());
//
// 		// 잘못된 입력
// 		UserReq invalidUserReq = new UserReq();
// 		invalidUserReq.setUid("test");
// 		invalidUserReq.setPassword("wrongpassword");
//
// 		mockMvc.perform(put("/login")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(mapper.writeValueAsString(invalidUserReq)))
// 			.andExpect(status().isNotFound());
// 	}
//
// 	// @Test
// 	// void setLogoutUser() throws Exception {
// 	// }
//
// 	// @Test
// 	// void removeUser() {
// 	// }
//
// 	// @Test
// 	// void getUser() {
// 	// }
//
// 	// @Test
// 	// void getMyChatRoom() {
// 	// }
//
// 	// @Test
// 	// void makeChat() {
// 	// }
//
// 	// @Test
// 	// void removeChat() {
// 	// }
// }