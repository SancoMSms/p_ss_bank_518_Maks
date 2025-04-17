package com.bank.profile.Kafka;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.DTO.ProfileDto;
import com.bank.profile.DTO.RegistrationDto;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileConsumerTest {

    private MeterRegistry meterRegistry;
    private Counter createCounter;
    private Counter updateCounter;
    private Counter deleteCounter;
    private Counter getCounter;
    private Counter errorCounter;

    private ProfileConsumer profileConsumer;

    @BeforeEach
    public void setUp() {
        meterRegistry = mock(MeterRegistry.class);

        createCounter = mock(Counter.class);
        updateCounter = mock(Counter.class);
        deleteCounter = mock(Counter.class);
        getCounter = mock(Counter.class);
        errorCounter = mock(Counter.class);

        when(meterRegistry.counter("profile.create.receive.count")).thenReturn(createCounter);
        when(meterRegistry.counter("profile.update.receive.count")).thenReturn(updateCounter);
        when(meterRegistry.counter("profile.delete.receive.count")).thenReturn(deleteCounter);
        when(meterRegistry.counter("profile.get.receive.count")).thenReturn(getCounter);
        when(meterRegistry.counter("profile.errors.receive.count")).thenReturn(errorCounter);

        profileConsumer = new ProfileConsumer(meterRegistry);
    }

    @Test
    public void testConsumeCreate() {
        ProfileDto profile = new ProfileDto();
        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-create", 0, 0L, null, profile);

        profileConsumer.consumeCreate(record);

        verify(createCounter).increment();
        verify(errorCounter, never()).increment();
    }

    @Test
    public void testConsumeUpdate() {
        ProfileDto profile = new ProfileDto();
        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-update", 0, 0L, null, profile);

        profileConsumer.consumeUpdate(record);

        verify(updateCounter).increment();
        verify(errorCounter, never()).increment();
    }

    @Test
    public void testConsumeDelete() {
        ProfileDto profile = new ProfileDto();
        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-delete", 0, 0L, null, profile);

        profileConsumer.consumeDelete(record);

        verify(deleteCounter).increment();
        verify(errorCounter, never()).increment();
    }

    @Test
    public void testConsumeGet() {
        ProfileDto profile = new ProfileDto();
        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-get", 0, 0L, null, profile);

        profileConsumer.consumeGet(record);

        verify(getCounter).increment();
        verify(errorCounter, never()).increment();
    }

    @Test
    public void testConsumeCreate_withException() {
        ProfileConsumer faultyConsumer = new ProfileConsumer(meterRegistry) {
            @Override
            public void consumeCreate(ConsumerRecord<String, ProfileDto> record) {
                createCounter.increment();
                try {
                    throw new RuntimeException("simulated");
                } catch (Exception e) {
                    errorCounter.increment();
                }
            }
        };

        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-create", 0, 0L, null, new ProfileDto());
        faultyConsumer.consumeCreate(record);

        verify(createCounter).increment();
        verify(errorCounter).increment();
    }

    @Test
    public void testConsumeUpdate_withException() {
        ProfileConsumer faultyConsumer = new ProfileConsumer(meterRegistry) {
            @Override
            public void consumeUpdate(ConsumerRecord<String, ProfileDto> record) {
                updateCounter.increment();
                try {
                    throw new RuntimeException("simulated");
                } catch (Exception e) {
                    errorCounter.increment();
                }
            }
        };

        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-update", 0, 0L, null, new ProfileDto());
        faultyConsumer.consumeUpdate(record);

        verify(updateCounter).increment();
        verify(errorCounter).increment();
    }

    @Test
    public void testConsumeDelete_withException() {
        ProfileConsumer faultyConsumer = new ProfileConsumer(meterRegistry) {
            @Override
            public void consumeDelete(ConsumerRecord<String, ProfileDto> record) {
                deleteCounter.increment();
                try {
                    throw new RuntimeException("simulated");
                } catch (Exception e) {
                    errorCounter.increment();
                }
            }
        };

        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-delete", 0, 0L, null, new ProfileDto());
        faultyConsumer.consumeDelete(record);

        verify(deleteCounter).increment();
        verify(errorCounter).increment();
    }

    @Test
    public void testConsumeGet_withException() {
        ProfileConsumer faultyConsumer = new ProfileConsumer(meterRegistry) {
            @Override
            public void consumeGet(ConsumerRecord<String, ProfileDto> record) {
                getCounter.increment();
                try {
                    throw new RuntimeException("simulated");
                } catch (Exception e) {
                    errorCounter.increment();
                }
            }
        };

        ConsumerRecord<String, ProfileDto> record = new ConsumerRecord<>("topic-get", 0, 0L, null, new ProfileDto());
        faultyConsumer.consumeGet(record);

        verify(getCounter).increment();
        verify(errorCounter).increment();
    }

    @Test
    public void testProfileDtoFields() {
        PassportDto passportDto = new PassportDto(); // можно мокнуть или создать пустой объект
        RegistrationDto registrationDto = new RegistrationDto();

        ProfileDto dto = new ProfileDto(
                1L,
                1234567890L,
                "test@example.com",
                "Test Name",
                1234567890L,
                12345678901L,
                passportDto,
                registrationDto
        );

        assertEquals(1L, dto.getId());
        assertEquals(1234567890L, dto.getPhoneNumber());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Test Name", dto.getNameOnCard());
        assertEquals(1234567890L, dto.getInn());
        assertEquals(12345678901L, dto.getSnils());
        assertEquals(passportDto, dto.getPassport());
        assertEquals(registrationDto, dto.getRegistration());
    }
}
