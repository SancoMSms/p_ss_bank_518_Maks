package com.bank.profile.Kafka;

import com.bank.profile.DTO.ProfileDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProfileConsumer {

    @KafkaListener(topics = "${spring.kafka.topics.profile-create}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeCreate(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received create profile: " + record.value());
        // обработка создания профиля
    }

    @KafkaListener(topics = "${spring.kafka.topics.profile-update}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUpdate(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received update profile: " + record.value());
        // обработка обновления профиля
    }

    @KafkaListener(topics = "${spring.kafka.topics.profile-delete}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeDelete(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received delete profile: " + record.value());
        // обработка удаления профиля
    }

    @KafkaListener(topics = "${spring.kafka.topics.profile-get}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeGet(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received get profile: " + record.value());
        // обработка получения профиля
    }
}
