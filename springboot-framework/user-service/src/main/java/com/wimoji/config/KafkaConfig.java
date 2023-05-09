package com.wimoji.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.common.JwtTokenUtil;
import com.wimoji.repository.UserRepository;
import com.wimoji.repository.dto.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.SimpleKafkaHeaderMapper;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
//    private final UserRepository userRepository;
//    private final JwtTokenUtil jwtTokenUtil;
//    private final ObjectMapper mapper;
//    @KafkaListener(id="server", topics = "userRequest")
//    @SendTo // use default replyTo expression
//    public String userRequest(String bearerToken) {
//
//        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
//            return null;
//        }
//
//        String token = bearerToken.substring(7);
//        String uid = jwtTokenUtil.getUserIdFromToken(token);
//        String nickname = jwtTokenUtil.getUserNicknameFromToken(token);
//        UserEntity user = UserEntity.builder().uid(uid).nickname(nickname).build();
//
//        String userStr = null;
//        try {
//            userStr = mapper.writeValueAsString(user);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return userStr;
//    }
//
//    @Bean
//    public NewTopic kRequests() {
//        return new NewTopic("kRequests", 10, (short) 2);
//    }
//
//    @Bean // not required if Jackson is on the classpath
//    public MessagingMessageConverter simpleMapperConverter() {
//        MessagingMessageConverter messagingMessageConverter = new MessagingMessageConverter();
//        messagingMessageConverter.setHeaderMapper(new SimpleKafkaHeaderMapper());
//        return messagingMessageConverter;
//    }
}
