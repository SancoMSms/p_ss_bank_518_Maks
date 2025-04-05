package com.bank.profile.Kafka;

import com.bank.profile.DTO.ProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProfileProducer {
    @Value("${spring.kafka.topics.profile-create}")
    private String profileCreate;
    @Value("${spring.kafka.topics.profile-update}")
    private String profileUpdate;
    @Value("${spring.kafka.topics.profile-delete}")
    private String profileDelete;
    @Value("${spring.kafka.topics.profile-get}")
    private String profileGet;

    private final KafkaTemplate<String, ProfileDto> kafkaTemplate;

    public ProfileProducer(KafkaTemplate<String, ProfileDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCreateProfile(ProfileDto profile) {
        kafkaTemplate.send(profileCreate, profile);
    }

    public void sendUpdateProfile(ProfileDto profile) {
        kafkaTemplate.send(profileUpdate, profile);
    }

    public void sendDeleteProfile(ProfileDto profile) {
        kafkaTemplate.send(profileDelete, profile);
    }

    public void sendGetProfile(ProfileDto profile) {
        kafkaTemplate.send(profileGet, profile);
    }
}
