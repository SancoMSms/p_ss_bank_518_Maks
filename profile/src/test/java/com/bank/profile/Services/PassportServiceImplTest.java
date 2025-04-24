package com.bank.profile.Services;

import com.bank.profile.DTO.PassportDto;
import com.bank.profile.Entities.Passport;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.PassportMapper;
import com.bank.profile.Repositories.PassportRepository;
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
class PassportServiceImplTest {

    private static final String CREATE_COUNT = "passport.create.count";
    private static final String UPDATE_COUNT = "passport.update.count";
    private static final String DELETE_COUNT = "passport.delete.count";
    private static final String ERROR_COUNT = "passport.errors.count";

    @Mock
    private final PassportRepository passportRepository = mock(PassportRepository.class);

    @Mock
    private final PassportMapper passportMapper = mock(PassportMapper.class);

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
    private PassportServiceImpl service;

    private PassportDto dto;
    private Passport entity;

    @BeforeEach
    void setup() {
        dto = new PassportDto();
        entity = new Passport();

        when(meterRegistry.counter(CREATE_COUNT)).thenReturn(createCounter);
        when(meterRegistry.counter(UPDATE_COUNT)).thenReturn(updateCounter);
        when(meterRegistry.counter(DELETE_COUNT)).thenReturn(deleteCounter);
        when(meterRegistry.counter(ERROR_COUNT)).thenReturn(errorCounter);

        service = new PassportServiceImpl(passportRepository, passportMapper, kafkaErrorProducer, meterRegistry);
    }

    @Test
    void testCreate_shouldSaveEntity() {
        when(passportMapper.toEntity(dto)).thenReturn(entity);
        when(passportRepository.save(entity)).thenReturn(entity);

        Passport result = service.create(dto);

        verify(createCounter).increment();
        verify(passportRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate_shouldSaveEntity() {
        when(passportMapper.toEntity(dto)).thenReturn(entity);
        when(passportRepository.save(entity)).thenReturn(entity);

        Passport result = service.update(dto);

        verify(updateCounter).increment();
        verify(passportRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDelete_shouldDeleteById() {
        Long id = 1L;
        service.delete(id);

        verify(deleteCounter).increment();
        verify(passportRepository).deleteById(id);
    }

    @Test
    void testGetPassport_shouldReturnEntity() {
        Long id = 1L;
        when(passportRepository.getById(id)).thenReturn(entity);

        Passport result = service.getPassport(id);

        verify(passportRepository).getById(id);
        assertEquals(entity, result);
    }

    @Test
    void testGetAllPassports_shouldReturnAll() {
        List<Passport> list = Arrays.asList(entity, new Passport());
        when(passportRepository.findAll()).thenReturn(list);

        List<Passport> result = service.getAllPassports();

        verify(passportRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testGetPassport_shouldSendErrorOnException() {
        Long id = 1L;
        RuntimeException exception = new RuntimeException("Get failed");
        when(passportRepository.getById(id)).thenThrow(exception);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.getPassport(id));
        assertEquals("Get failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getPassport"));
    }

    @Test
    void testDelete_shouldSendErrorOnException() {
        Long id = 1L;
        doThrow(new RuntimeException("Delete failed")).when(passportRepository).deleteById(id);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertEquals("Delete failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("delete"));
    }

    @Test
    void testCreate_shouldSendErrorOnException() {
        when(passportMapper.toEntity(dto)).thenReturn(entity);
        when(passportRepository.save(entity)).thenThrow(new RuntimeException("Create failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertEquals("Create failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("create"));
    }

    @Test
    void testUpdate_shouldSendErrorOnException() {
        when(passportMapper.toEntity(dto)).thenReturn(entity);
        when(passportRepository.save(entity)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.update(dto));
        assertEquals("Update failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("update"));
    }

    @Test
    void testGetAllPassports_shouldSendErrorOnException() {
        when(passportRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.getAllPassports());
        assertEquals("Fetch failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getAllPassports"));
    }
}
