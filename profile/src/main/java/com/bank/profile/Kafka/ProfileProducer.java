package com.bank.profile.Kafka;

import com.bank.profile.DTO.ProfileDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileProducer {

    private final KafkaTemplate<String, ProfileDto> kafkaTemplate;

    public ProfileProducer(KafkaTemplate<String, ProfileDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCreateProfile(ProfileDto profile) {
        kafkaTemplate.send("profile.create", profile);
    }

    public void sendUpdateProfile(ProfileDto profile) {
        kafkaTemplate.send("profile.update", profile);
    }

    public void sendDeleteProfile(ProfileDto profile) {
        kafkaTemplate.send("profile.delete", profile);
    }

    public void sendGetProfile(ProfileDto profile) {
        kafkaTemplate.send("profile.get", profile);
    }
}
