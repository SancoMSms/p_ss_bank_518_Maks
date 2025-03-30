package com.bank.profile.Kafka;

import com.bank.profile.DTO.ProfileDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProfileConsumer {

    @KafkaListener(topics = "profile.create", groupId = "profile-group")
    public void consumeCreate(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received create profile: " + record.value());
        // обработка создания профиля
    }

    @KafkaListener(topics = "profile.update", groupId = "profile-group")
    public void consumeUpdate(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received update profile: " + record.value());
        // обработка обновления профиля
    }

    @KafkaListener(topics = "profile.delete", groupId = "profile-group")
    public void consumeDelete(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received delete profile: " + record.value());
        // обработка удаления профиля
    }

    @KafkaListener(topics = "profile.get", groupId = "profile-group")
    public void consumeGet(ConsumerRecord<String, ProfileDto> record) {
        System.out.println("Received get profile: " + record.value());
        // обработка получения профиля
    }
}
