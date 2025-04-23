package com.bank.profile.Kafka;

import com.bank.profile.DTO.ProfileDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProfileProducerTest {

    private KafkaTemplate<String, ProfileDto> kafkaTemplate;
    private MeterRegistry meterRegistry;

    private Counter createCounter;
    private Counter updateCounter;
    private Counter deleteCounter;
    private Counter getCounter;
    private Counter errorCounter;

    private ProfileProducer profileProducer;

    @BeforeEach
    public void setup() {
        kafkaTemplate = mock(KafkaTemplate.class);
        meterRegistry = mock(MeterRegistry.class);

        createCounter = mock(Counter.class);
        updateCounter = mock(Counter.class);
        deleteCounter = mock(Counter.class);
        getCounter = mock(Counter.class);
        errorCounter = mock(Counter.class);

        when(meterRegistry.counter("profile.create.count")).thenReturn(createCounter);
        when(meterRegistry.counter("profile.update.count")).thenReturn(updateCounter);
        when(meterRegistry.counter("profile.delete.count")).thenReturn(deleteCounter);
        when(meterRegistry.counter("profile.get.count")).thenReturn(getCounter);
        when(meterRegistry.counter("profile.errors.count")).thenReturn(errorCounter);

        profileProducer = new ProfileProducer(kafkaTemplate, meterRegistry);

        // Установка значений топиков
        setField(profileProducer, "profileCreate", "topic-create");
        setField(profileProducer, "profileUpdate", "topic-update");
        setField(profileProducer, "profileDelete", "topic-delete");
        setField(profileProducer, "profileGet", "topic-get");
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSendCreateProfile() {
        ProfileDto profile = new ProfileDto();
        profileProducer.sendCreateProfile(profile);

        verify(createCounter).increment();
        verify(kafkaTemplate).send("topic-create", profile);
    }

    @Test
    public void testSendUpdateProfile() {
        ProfileDto profile = new ProfileDto();
        profileProducer.sendUpdateProfile(profile);

        verify(updateCounter).increment();
        verify(kafkaTemplate).send("topic-update", profile);
    }

    @Test
    public void testSendDeleteProfile() {
        ProfileDto profile = new ProfileDto();
        profileProducer.sendDeleteProfile(profile);

        verify(deleteCounter).increment();
        verify(kafkaTemplate).send("topic-delete", profile);
    }

    @Test
    public void testSendGetProfile() {
        ProfileDto profile = new ProfileDto();
        profileProducer.sendGetProfile(profile);

        verify(getCounter).increment();
        verify(kafkaTemplate).send("topic-get", profile);
    }

    @Test
    public void testSendCreateProfile_withException() {
        ProfileDto profile = new ProfileDto();

        doThrow(new RuntimeException("Kafka error")).when(kafkaTemplate).send("topic-create", profile);

        profileProducer.sendCreateProfile(profile);

        verify(createCounter).increment();
        verify(errorCounter).increment();
    }
}
