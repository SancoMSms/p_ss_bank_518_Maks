package com.bank.profile.Services;

import com.bank.profile.DTO.ProfileDto;
import com.bank.profile.Entities.Profile;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.ProfileMapper;
import com.bank.profile.Repositories.ProfileRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    private static final String CREATE_COUNT = "profile.create.count";
    private static final String UPDATE_COUNT = "profile.update.count";
    private static final String DELETE_COUNT = "profile.delete.count";
    private static final String ERROR_COUNT = "profile.errors.count";

    @Mock
    private final ProfileRepository profileRepository = mock(ProfileRepository.class);

    @Mock
    private final ProfileMapper profileMapper = mock(ProfileMapper.class);

    @Mock
    private final KafkaErrorProducer kafkaErrorProducer = mock(KafkaErrorProducer.class);

    @Mock
    private final MeterRegistry meterRegistry = mock(MeterRegistry.class);

    @Mock
    private final Counter createCounter = mock(Counter.class);

    @Mock
    private final Counter updateCounter = mock(Counter.class);

    @Mock
    private final Counter deleteCounter = mock(Counter.class);

    @Mock
    private final Counter errorCounter = mock(Counter.class);

    @InjectMocks
    private ProfileServiceImpl service;

    private ProfileDto dto;
    private Profile entity;

    @BeforeEach
    void setup() {
        dto = new ProfileDto();
        entity = new Profile();

        when(meterRegistry.counter(CREATE_COUNT)).thenReturn(createCounter);
        when(meterRegistry.counter(UPDATE_COUNT)).thenReturn(updateCounter);
        when(meterRegistry.counter(DELETE_COUNT)).thenReturn(deleteCounter);
        when(meterRegistry.counter(ERROR_COUNT)).thenReturn(errorCounter);

        service = new ProfileServiceImpl(profileRepository, profileMapper, kafkaErrorProducer, meterRegistry);
    }

    @Test
    void testCreate_shouldSaveEntity() {
        when(profileMapper.toEntity(dto)).thenReturn(entity);
        when(profileRepository.save(entity)).thenReturn(entity);

        Profile result = service.create(dto);

        verify(createCounter).increment();
        verify(profileRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate_shouldSaveEntity() {
        when(profileMapper.toEntity(dto)).thenReturn(entity);
        when(profileRepository.save(entity)).thenReturn(entity);

        Profile result = service.update(dto);

        verify(updateCounter).increment();
        verify(profileRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDelete_shouldDeleteById() {
        Long id = 1L;
        service.delete(id);

        verify(deleteCounter).increment();
        verify(profileRepository).deleteById(id);
    }

    @Test
    void testGetProfile_shouldReturnEntity() {
        Long id = 1L;
        when(profileRepository.getById(id)).thenReturn(entity);

        Profile result = service.getProfile(id);

        verify(profileRepository).getById(id);
        assertEquals(entity, result);
    }

    @Test
    void testGetAllProfiles_shouldReturnAll() {
        List<Profile> list = Arrays.asList(entity, new Profile());
        when(profileRepository.findAll()).thenReturn(list);

        List<Profile> result = service.getAllProfiles();

        verify(profileRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testGetProfile_shouldSendErrorOnException() {
        Long id = 1L;
        RuntimeException exception = new RuntimeException("Get failed");
        when(profileRepository.getById(id)).thenThrow(exception);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.getProfile(id));
        assertEquals("Get failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getProfile"));
    }

    @Test
    void testDelete_shouldSendErrorOnException() {
        Long id = 1L;
        doThrow(new RuntimeException("Delete failed")).when(profileRepository).deleteById(id);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertEquals("Delete failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("delete"));
    }

    @Test
    void testCreate_shouldSendErrorOnException() {
        when(profileMapper.toEntity(dto)).thenReturn(entity);
        when(profileRepository.save(entity)).thenThrow(new RuntimeException("Create failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertEquals("Create failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("create"));
    }

    @Test
    void testUpdate_shouldSendErrorOnException() {
        when(profileMapper.toEntity(dto)).thenReturn(entity);
        when(profileRepository.save(entity)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.update(dto));
        assertEquals("Update failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("update"));
    }

    @Test
    void testGetAllProfiles_shouldSendErrorOnException() {
        when(profileRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.getAllProfiles());
        assertEquals("Fetch failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getAllProfiles"));
    }
}
