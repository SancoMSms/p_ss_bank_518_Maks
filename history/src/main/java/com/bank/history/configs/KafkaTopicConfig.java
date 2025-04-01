package com.bank.history.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.topics.audit-history}")
    private String auditHistoryTopic;

    @Value("${spring.kafka.topics.audit-history-request}")
    private String auditHistoryRequestTopic;

    @Value("${spring.kafka.topics.audit-history-response}")
    private String auditHistoryResponseTopic;

    @Bean
    public NewTopic auditHistoryTopic() {
        return new NewTopic(auditHistoryTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic auditHistoryRequestTopic() {
        return new NewTopic(auditHistoryRequestTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic auditHistoryResponseTopic() {
        return new NewTopic(auditHistoryResponseTopic, 1, (short) 1);
    }
}