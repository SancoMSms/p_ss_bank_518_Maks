package com.bank.profile.Services;

import com.bank.profile.DTO.AccountDetailsDto;
import com.bank.profile.Entities.AccountDetails;
import com.bank.profile.Kafka.KafkaErrorProducer;
import com.bank.profile.Mappers.AccountDetailsMapper;
import com.bank.profile.Repositories.AccountDetailsRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceImplTest {

    private static final String CREATE_METRIC = "accountDetails.create.count";
    private static final String UPDATE_METRIC = "accountDetails.update.count";
    private static final String DELETE_METRIC = "accountDetails.delete.count";
    private static final String ERROR_METRIC = "accountDetails.errors.count";

    @Mock
    private final AccountDetailsRepository accountDetailsRepository = null;

    @Mock
    private final AccountDetailsMapper accountDetailsMapper = null;

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
    private AccountDetailsServiceImpl service;

    private AccountDetailsDto dto;
    private AccountDetails entity;

    @BeforeEach
    void setup() {
        dto = new AccountDetailsDto();
        entity = new AccountDetails();

        when(meterRegistry.counter(CREATE_METRIC)).thenReturn(createCounter);
        when(meterRegistry.counter(UPDATE_METRIC)).thenReturn(updateCounter);
        when(meterRegistry.counter(DELETE_METRIC)).thenReturn(deleteCounter);
        when(meterRegistry.counter(ERROR_METRIC)).thenReturn(errorCounter);

        service = new AccountDetailsServiceImpl(accountDetailsRepository, accountDetailsMapper, kafkaErrorProducer, meterRegistry);
    }

    @Test
    @DisplayName("Должен сохранить AccountDetails и вернуть сущность")
    void testCreate_shouldSaveEntity() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenReturn(entity);

        AccountDetails result = service.create(dto);

        verify(createCounter).increment();
        verify(accountDetailsRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    @DisplayName("Должен обновить AccountDetails и вернуть сущность")
    void testUpdate_shouldSaveEntity() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenReturn(entity);

        AccountDetails result = service.update(dto);

        verify(updateCounter).increment();
        verify(accountDetailsRepository).save(entity);
        assertEquals(entity, result);
    }

    @Test
    @DisplayName("Должен удалить AccountDetails по ID")
    void testDelete_shouldDeleteById() {
        Long id = 1L;
        service.delete(id);

        verify(deleteCounter).increment();
        verify(accountDetailsRepository).deleteById(id);
    }

    @Test
    @DisplayName("Должен вернуть AccountDetails по ID")
    void testGetAccountDetails_shouldReturnEntity() {
        Long id = 1L;
        when(accountDetailsRepository.getById(id)).thenReturn(entity);

        AccountDetails result = service.getAccountDetails(id);

        verify(accountDetailsRepository).getById(id);
        assertEquals(entity, result);
    }

    @Test
    @DisplayName("Должен вернуть все AccountDetails")
    void testGetAllAccountDetails_shouldReturnAll() {
        List<AccountDetails> list = Arrays.asList(entity, new AccountDetails());
        when(accountDetailsRepository.findAll()).thenReturn(list);

        List<AccountDetails> result = service.getAllAccountDetails();

        verify(accountDetailsRepository).findAll();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Должен отправить ошибку в Kafka при исключении в getAccountDetails")
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
    @DisplayName("Должен отправить ошибку в Kafka при исключении в delete")
    void testDelete_shouldSendErrorOnException() {
        Long id = 1L;
        doThrow(new RuntimeException("Delete failed")).when(accountDetailsRepository).deleteById(id);

        Exception thrown = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertEquals("Delete failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("delete"));
    }

    @Test
    @DisplayName("Должен отправить ошибку в Kafka при исключении в create")
    void testCreate_shouldSendErrorOnException() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenThrow(new RuntimeException("Create failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.create(dto));
        assertEquals("Create failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("create"));
    }

    @Test
    @DisplayName("Должен отправить ошибку в Kafka при исключении в update")
    void testUpdate_shouldSendErrorOnException() {
        when(accountDetailsMapper.toEntity(dto)).thenReturn(entity);
        when(accountDetailsRepository.save(entity)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.update(dto));
        assertEquals("Update failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("update"));
    }

    @Test
    @DisplayName("Должен отправить ошибку в Kafka при исключении в getAllAccountDetails")
    void testGetAllAccountDetails_shouldSendErrorOnException() {
        when(accountDetailsRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> service.getAllAccountDetails());
        assertEquals("Fetch failed", thrown.getMessage());

        verify(errorCounter).increment();
        verify(kafkaErrorProducer).sendError(contains("getAllAccountDetails"));
    }
}
