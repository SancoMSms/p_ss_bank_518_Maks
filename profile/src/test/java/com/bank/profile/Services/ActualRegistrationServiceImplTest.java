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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ActualRegistrationServiceImplTest {

    @Mock
    private ActualRegistrationRepository actualRegistrationRepository;

    @Mock
    private ActualRegistrationMapper actualRegistrationMapper;

    @Mock
    private KafkaErrorProducer kafkaErrorProducer;

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private Counter createCounter;

    @Mock
    private Counter updateCounter;

    @Mock
    private Counter deleteCounter;

    @Mock
    private Counter errorCounter;

    private ActualRegistrationServiceImpl actualRegistrationService;

    @BeforeEach
    public void setup() {
        when(meterRegistry.counter("actualRegistration.create.count")).thenReturn(createCounter);
        when(meterRegistry.counter("actualRegistration.update.count")).thenReturn(updateCounter);
        when(meterRegistry.counter("actualRegistration.delete.count")).thenReturn(deleteCounter);
        when(meterRegistry.counter("actualRegistration.errors.count")).thenReturn(errorCounter);

        doNothing().when(createCounter).increment();
        doNothing().when(updateCounter).increment();
        doNothing().when(deleteCounter).increment();
        doNothing().when(errorCounter).increment();

        actualRegistrationService = new ActualRegistrationServiceImpl(
                actualRegistrationRepository,
                actualRegistrationMapper,
                kafkaErrorProducer,
                meterRegistry
        );
    }

    @Test
    public void testCreate() {
        ActualRegistrationDto dto = mock(ActualRegistrationDto.class);
        ActualRegistration entity = mock(ActualRegistration.class);

        when(actualRegistrationMapper.toEntity(dto)).thenReturn(entity);
        when(actualRegistrationRepository.save(entity)).thenReturn(entity);

        ActualRegistration result = actualRegistrationService.create(dto);

        verify(actualRegistrationRepository, times(1)).save(entity);
        verify(createCounter, times(1)).increment();
    }

    @Test
    public void testUpdate() {
        ActualRegistrationDto dto = mock(ActualRegistrationDto.class);
        ActualRegistration entity = mock(ActualRegistration.class);

        when(actualRegistrationMapper.toEntity(dto)).thenReturn(entity);
        when(actualRegistrationRepository.save(entity)).thenReturn(entity);

        ActualRegistration result = actualRegistrationService.update(dto);

        verify(actualRegistrationRepository, times(1)).save(entity);
        verify(updateCounter, times(1)).increment();
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        doNothing().when(actualRegistrationRepository).deleteById(id);

        actualRegistrationService.delete(id);

        verify(actualRegistrationRepository, times(1)).deleteById(id);
        verify(deleteCounter, times(1)).increment();
    }

    @Test
    public void testCreateShouldHandleError() {
        ActualRegistrationDto dto = mock(ActualRegistrationDto.class);
        ActualRegistration entity = mock(ActualRegistration.class);

        when(actualRegistrationMapper.toEntity(dto)).thenReturn(entity);
        when(actualRegistrationRepository.save(entity)).thenThrow(new RuntimeException("Create failed"));

        doNothing().when(errorCounter).increment();

        try {
            actualRegistrationService.create(dto);
        } catch (Exception e) {
            verify(errorCounter, times(1)).increment();
            verify(kafkaErrorProducer, times(1)).sendError(anyString());
        }
    }

    @Test
    public void testUpdateShouldThrowExceptionIfNotFound() {
        ActualRegistrationDto dto = mock(ActualRegistrationDto.class);
        when(actualRegistrationMapper.toEntity(dto)).thenReturn(new ActualRegistration());

        when(actualRegistrationRepository.save(any(ActualRegistration.class))).thenThrow(new RuntimeException("Not Found"));

        try {
            actualRegistrationService.update(dto);
        } catch (Exception e) {
            verify(kafkaErrorProducer, times(1)).sendError(anyString());
        }
    }

    @Test
    public void testDeleteShouldHandleNotFound() {
        Long id = 1L;

        doThrow(new RuntimeException("Not Found")).when(actualRegistrationRepository).deleteById(id);

        try {
            actualRegistrationService.delete(id);
        } catch (Exception e) {
            verify(kafkaErrorProducer, times(1)).sendError(anyString());
        }
    }

    @Test
    public void testGetActualRegistration() {
        Long id = 1L;
        ActualRegistration entity = mock(ActualRegistration.class);

        when(actualRegistrationRepository.getById(id)).thenReturn(entity);

        ActualRegistration result = actualRegistrationService.getActualRegistration(id);

        verify(actualRegistrationRepository, times(1)).getById(id);
    }

    @Test
    public void testGetAllActualRegistrations() {
        when(actualRegistrationRepository.findAll()).thenReturn(List.of(mock(ActualRegistration.class)));

        actualRegistrationService.getAllActualRegistrations();

        verify(actualRegistrationRepository, times(1)).findAll();
    }

    // Дополнительный тест для проверки работы с ошибками
    @Test
    public void testGetActualRegistrationShouldHandleError() {
        Long id = 1L;

        when(actualRegistrationRepository.getById(id)).thenThrow(new RuntimeException("Not Found"));

        try {
            actualRegistrationService.getActualRegistration(id);
        } catch (Exception e) {
            verify(kafkaErrorProducer, times(1)).sendError(anyString());
        }
    }

    // Дополнительный тест для обработки ошибок при запросе всех записей
    @Test
    public void testGetAllActualRegistrationsShouldHandleError() {
        when(actualRegistrationRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        try {
            actualRegistrationService.getAllActualRegistrations();
        } catch (Exception e) {
            verify(kafkaErrorProducer, times(1)).sendError(anyString());
        }
    }
}
