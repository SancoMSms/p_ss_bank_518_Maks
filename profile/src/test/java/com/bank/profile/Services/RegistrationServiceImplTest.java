package com.bank.profile.Services;

import com.bank.profile.DTO.RegistrationDto;
import com.bank.profile.Entities.Registration;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.RegistrationMappers;
import com.bank.profile.Repositories.RegistrationRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    private static final String CREATE_COUNT = "registration.create.count";
    private static final String UPDATE_COUNT = "registration.update.count";
    private static final String DELETE_COUNT = "registration.delete.count";
    private static final String ERROR_COUNT = "registration.errors.count";

    @Mock
    private final RegistrationRepository registrationRepository = mock(RegistrationRepository.class);

    @Mock
    private final RegistrationMappers registrationMappers = mock(RegistrationMappers.class);

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
    private RegistrationServiceImpl service;

    private RegistrationDto dto;
    private Registration entity;

    @BeforeEach
    void setup() {
        dto = new RegistrationDto();
        entity = new Registration();

        when(meterRegistry.counter(CREATE_COUNT)).thenReturn(createCounter);
        when(meterRegistry.counter(UPDATE_COUNT)).thenReturn(updateCounter);
        when(meterRegistry.counter(DELETE_COUNT)).thenReturn(deleteCounter);
        when(meterRegistry.counter(ERROR_COUNT)).thenReturn(errorCounter);

        service = new RegistrationServiceImpl(registrationRepository, registrationMappers, kafkaErrorProducer, meterRegistry);
    }

    @Test
    void testCreate_shouldSaveEntity() {
        when(registrationMappers.toEntity(dto)).thenReturn(entity);
        when(registrationRepository.save(entity)).thenReturn(entity);

        Registration result = service.create(dto);

        verify(createCounter).increment();
        verify(registrationRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate_shouldSaveEntity() {
        when(registrationMappers.toEntity(dto)).thenReturn(entity);
        when(registrationRepository.save(entity)).thenReturn(entity);

        Registration result = service.update(dto);

        verify(updateCounter).increment();
        verify(registrationRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDelete_shouldDeleteById() {
        Long id = 1L;
        service.delete(id);

        verify(deleteCounter).increment();
        verify(registrationRepository).deleteById(id);
    }

    @Test
    void testGetRegistration_shouldReturnEntity() {
        Long id = 1L;
        when(registrationRepository.getById(id)).thenReturn(entity);

        Registration result = service.getRegistration(id);

        verify(registrationRepository).getById(id);
        assertEquals(entity, result);
    }

    @Test
    void testGetAllRegistrations_shouldReturnAll() {
        List<Registration> list = Arrays.asList(entity, new Registration());
        when(registrationRepository.findAll()).thenReturn(list);

        List<Registration> result = service.getAllRegistrations();

        verify(registrationRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testGetRegistration_shouldSendErrorOnException() {
        Long id = 1L;
        RuntimeException exception = new RuntimeException("Get failed");
        when(registrationRepository.getById(id)).thenThrow(exception);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.getRegistration(id));
        assertEquals("Get failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getRegistration"));
    }

    @Test
    void testDelete_shouldSendErrorOnException() {
        Long id = 1L;
        doThrow(new RuntimeException("Delete failed")).when(registrationRepository).deleteById(id);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertEquals("Delete failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("delete"));
    }

    @Test
    void testCreate_shouldSendErrorOnException() {
        when(registrationMappers.toEntity(dto)).thenReturn(entity);
        when(registrationRepository.save(entity)).thenThrow(new RuntimeException("Create failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertEquals("Create failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("create"));
    }

    @Test
    void testUpdate_shouldSendErrorOnException() {
        when(registrationMappers.toEntity(dto)).thenReturn(entity);
        when(registrationRepository.save(entity)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.update(dto));
        assertEquals("Update failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("update"));
    }

    @Test
    void testGetAllRegistrations_shouldSendErrorOnException() {
        when(registrationRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.getAllRegistrations());
        assertEquals("Fetch failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getAllRegistrations"));
    }
}
