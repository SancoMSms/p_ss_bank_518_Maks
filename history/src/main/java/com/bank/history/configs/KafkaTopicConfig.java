package com.bank.history.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic auditHistoryTopic() {
        return new NewTopic("audit.history", 1, (short) 1);
    }

    @Bean
    public NewTopic auditHistoryRequestTopic() {
        return new NewTopic("audit.history.request", 1, (short) 1);
    }

    @Bean
    public NewTopic auditHistoryResponseTopic() {
        return new NewTopic("audit.history.response", 1, (short) 1);
    }
}