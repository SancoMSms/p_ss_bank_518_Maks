package com.bank.profile.Services;

import com.bank.profile.DTO.ActualRegistrationDto;
import com.bank.profile.Entities.ActualRegistration;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.ActualRegistrationMapper;
import com.bank.profile.Repositories.ActualRegistrationRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActualRegistrationServiceImplTest {

    private static final String CREATE_METRIC = "actualRegistration.create.count";
    private static final String UPDATE_METRIC = "actualRegistration.update.count";
    private static final String DELETE_METRIC = "actualRegistration.delete.count";
    private static final String ERROR_METRIC = "actualRegistration.errors.count";

    @Mock
    private final ActualRegistrationRepository actualRegistrationRepository = null;

    @Mock
    private final ActualRegistrationMapper actualRegistrationMapper = null;

    @Mock
    private final KafkaErrorProducer kafkaErrorProducer = null;

    @Mock
    private final MeterRegistry meterRegistry = null;

    @Mock
    private final Counter createCounter = null;

    @Mock
    private final Counter updateCounter = null;

    @Mock
    private final Counter deleteCounter = null;

    @Mock
    private final Counter errorCounter = null;

    @InjectMocks
    private ActualRegistrationServiceImpl service;

    private ActualRegistrationDto dto;
    private ActualRegistration entity;

    @BeforeEach
    void setup() {
        dto = new ActualRegistrationDto();
        entity = new ActualRegistration();

        when(meterRegistry.counter(CREATE_METRIC)).thenReturn(createCounter);
        when(meterRegistry.counter(UPDATE_METRIC)).thenReturn(updateCounter);
        when(meterRegistry.counter(DELETE_METRIC)).thenReturn(deleteCounter);
        when(meterRegistry.counter(ERROR_METRIC)).thenReturn(errorCounter);

        service = new ActualRegistrationServiceImpl(actualRegistrationRepository, actualRegistrationMapper, kafkaErrorProducer, meterRegistry);
    }

    @Test
    void testCreate_shouldSaveEntity() {
        when(actualRegistrationMapper.toEntity(dto)).thenReturn(entity);
        when(actualRegistrationRepository.save(entity)).thenReturn(entity);

        ActualRegistration result = service.create(dto);

        verify(createCounter).increment();
        verify(actualRegistrationRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate_shouldSaveEntity() {
        when(actualRegistrationMapper.toEntity(dto)).thenReturn(entity);
        when(actualRegistrationRepository.save(entity)).thenReturn(entity);

        ActualRegistration result = service.update(dto);

        verify(updateCounter).increment();
        verify(actualRegistrationRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDelete_shouldDeleteById() {
        Long id = 1L;
        service.delete(id);

        verify(deleteCounter).increment();
        verify(actualRegistrationRepository).deleteById(id);
    }

    @Test
    void testCreate_shouldSendErrorOnException() {
        when(actualRegistrationMapper.toEntity(dto)).thenReturn(entity);
        when(actualRegistrationRepository.save(entity)).thenThrow(new RuntimeException("Create failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertEquals("Create failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("create"));
    }

    @Test
    void testUpdate_shouldSendErrorOnException() {
        when(actualRegistrationMapper.toEntity(dto)).thenReturn(entity);
        when(actualRegistrationRepository.save(entity)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.update(dto));
        assertEquals("Update failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("update"));
    }

    @Test
    void testDelete_shouldSendErrorOnException() {
        Long id = 1L;
        doThrow(new RuntimeException("Delete failed")).when(actualRegistrationRepository).deleteById(id);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertEquals("Delete failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("delete"));
    }

    @Test
    void testGetActualRegistration_shouldSendErrorOnException() {
        Long id = 1L;
        when(actualRegistrationRepository.getById(id)).thenThrow(new RuntimeException("Not Found"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.getActualRegistration(id));
        assertEquals("Not Found", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getActualRegistration"));
    }

    @Test
    void testGetAllActualRegistrations_shouldSendErrorOnException() {
        when(actualRegistrationRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.getAllActualRegistrations());
        assertEquals("Fetch failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getAllActualRegistrations"));
    }
}
