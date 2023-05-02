package com.wimoji.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.Entity.User;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaConfig {
    private static ObjectMapper mapper;
    @Autowired
    public KafkaConfig(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static User getUserByToken(ReplyingKafkaTemplate<String, String, String> template, String bearerToken){

        SendResult<String, String> sendResult = null;
        ConsumerRecord<String, String> consumerRecord = null;
        User user = null;

        try {
            if (!template.waitForAssignment(Duration.ofSeconds(10))) {
                throw new IllegalStateException("Reply container did not initialize");
            }
            ProducerRecord<String, String> record = new ProducerRecord<>("userRequest", bearerToken);
            RequestReplyFuture<String, String, String> replyFuture = template.sendAndReceive(record);
            sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);

            System.out.println("Sent ok: " + sendResult.getRecordMetadata());
            consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);

            System.out.println("Return value: " + consumerRecord.value());
            String tmp = consumerRecord.value();

            user = mapper.readValue(tmp, User.class);
        } catch (Exception e) {
            throw new GeneralException(Code.UNAUTHORIZED);
        }
        return user;
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
            ProducerFactory<String, String> pf,
            ConcurrentMessageListenerContainer<String, String> repliesContainer) {

        return new ReplyingKafkaTemplate<>(pf, repliesContainer);
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesContainer(
            ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {

        ConcurrentMessageListenerContainer<String, String> repliesContainer =
                containerFactory.createContainer("kReplies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public NewTopic kRequests() {
        return TopicBuilder.name("kRequests")
                .partitions(10)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic kReplies() {
        return TopicBuilder.name("kReplies")
                .partitions(10)
                .replicas(2)
                .build();
    }
}
