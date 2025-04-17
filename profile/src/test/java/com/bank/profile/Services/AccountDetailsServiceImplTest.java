package com.bank.profile.Services;

import com.bank.profile.DTO.AccountDetailsDto;
import com.bank.profile.Entities.AccountDetails;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.AccountDetailsMapper;
import com.bank.profile.Repositories.AccountDetailsRepository;
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
class AccountDetailsServiceImplTest {

    @Mock
    private AccountDetailsRepository accountDetailsRepository;
    @Mock
    private AccountDetailsMapper accountDetailsMapper;
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

    @InjectMocks
    private AccountDetailsServiceImpl service;

    private AccountDetailsDto dto;
    private AccountDetails entity;

    @BeforeEach
    void setup() {
        dto = new AccountDetailsDto();
        entity = new AccountDetails();

        when(meterRegistry.counter("accountDetails.create.count")).thenReturn(createCounter);
        when(meterRegistry.counter("accountDetails.update.count")).thenReturn(updateCounter);
        when(meterRegistry.counter("accountDetails.delete.count")).thenReturn(deleteCounter);
        when(meterRegistry.counter("accountDetails.errors.count")).thenReturn(errorCounter);

        service = new AccountDetailsServiceImpl(accountDetailsRepository, accountDetailsMapper, kafkaErrorProducer, meterRegistry);
    }

    @Test
    void testCreate_shouldSaveEntity() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenReturn(entity);

        AccountDetails result = service.create(dto);

        verify(createCounter).increment();
        verify(accountDetailsRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testUpdate_shouldSaveEntity() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenReturn(entity);

        AccountDetails result = service.update(dto);

        verify(updateCounter).increment();
        verify(accountDetailsRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    void testDelete_shouldDeleteById() {
        Long id = 1L;
        service.delete(id);

        verify(deleteCounter).increment();
        verify(accountDetailsRepository).deleteById(id);
    }

    @Test
    void testGetAccountDetails_shouldReturnEntity() {
        Long id = 1L;
        when(accountDetailsRepository.getById(id)).thenReturn(entity);

        AccountDetails result = service.getAccountDetails(id);

        verify(accountDetailsRepository).getById(id);
        assertEquals(entity, result);
    }

    @Test
    void testGetAllAccountDetails_shouldReturnAll() {
        List<AccountDetails> list = Arrays.asList(entity, new AccountDetails());
        when(accountDetailsRepository.findAll()).thenReturn(list);

        List<AccountDetails> result = service.getAllAccountDetails();

        verify(accountDetailsRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAccountDetails_shouldSendErrorOnException() {
        Long id = 1L;
        RuntimeException exception = new RuntimeException("Get failed");
        when(accountDetailsRepository.getById(id)).thenThrow(exception);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.getAccountDetails(id));
        assertEquals("Get failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getAccountDetails"));
    }

    @Test
    void testDelete_shouldSendErrorOnException() {
        Long id = 1L;
        doThrow(new RuntimeException("Delete failed")).when(accountDetailsRepository).deleteById(id);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertEquals("Delete failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("delete"));
    }

    @Test
    void testCreate_shouldSendErrorOnException() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenThrow(new RuntimeException("Create failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertEquals("Create failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("create"));
    }

    @Test
    void testUpdate_shouldSendErrorOnException() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.update(dto));
        assertEquals("Update failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("update"));
    }

    @Test
    void testGetAllAccountDetails_shouldSendErrorOnException() {
        when(accountDetailsRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.getAllAccountDetails());
        assertEquals("Fetch failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getAllAccountDetails"));
    }
}
