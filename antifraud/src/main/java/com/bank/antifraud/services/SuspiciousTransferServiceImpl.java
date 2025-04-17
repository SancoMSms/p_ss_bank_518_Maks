package com.bank.antifraud.services;

import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.dto.SuspiciousTransferDtoFactory;
import com.bank.antifraud.dto.TransferAntiFraudDto;
import com.bank.antifraud.entities.AbstractSuspiciousTransfer;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.mappers.SuspiciousTransferMapper;
import com.bank.antifraud.kafka.producer.SuspiciousTransferProducer;
import com.bank.antifraud.repositories.SuspiciousTransferRepositoryManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuspiciousTransferServiceImpl implements SuspiciousTransferService {

    private final SuspiciousTransferRepositoryManager repositoryManager;
    private final SuspiciousTransferDtoFactory suspiciousTransferDtoFactory;
    private final SuspiciousTransferProducer suspiciousTransferProducer;
    private final SuspiciousTransferMapper mapper;

    @Override
    public AbstractSuspiciousTransferDto createSuspiciousTransfer(@Valid TransferAntiFraudDto kafkaDto) {
        log.info("Creating suspicious transfer of type: {}", kafkaDto.getEntityType());
        final JpaRepository<AbstractSuspiciousTransfer, Long> repository =
                repositoryManager.getRepository(kafkaDto.getEntityType());
        validateExistsInDb(kafkaDto, repository);
        final AbstractSuspiciousTransferDto dto = toSuspiciousTransferDto(kafkaDto);
        if (dto.getIsBlocked()) {
            blockTransfer(kafkaDto);
        } else {
            approveTransfer(dto, repository);
        }
        return dto;
    }

    private void approveTransfer(AbstractSuspiciousTransferDto dto,
                                 JpaRepository<AbstractSuspiciousTransfer, Long> repository) {
        final AbstractSuspiciousTransfer entity = mapper.toEntity(dto);
        entity.syncIdWithTransferId();
        final AbstractSuspiciousTransfer savedEntity = repository.save(entity);
        final AbstractSuspiciousTransferDto savedDto = mapper.toDto(savedEntity);
        savedDto.setAmount(dto.getAmount());
        final TransferAntiFraudDto processedKafkaDto = toTransferAntiFraudDto(savedDto);
        suspiciousTransferProducer.sendApprovedEvent(processedKafkaDto);
        log.info("Transfer with ID: {} is approved", dto.getTransferId());
    }

    private void blockTransfer(TransferAntiFraudDto kafkaDto) {
        suspiciousTransferProducer.sendBlockedEvent(kafkaDto);
        log.info("Transfer with ID: {} is blocked", kafkaDto.getTransferId());
    }

    private AbstractSuspiciousTransferDto toSuspiciousTransferDto(TransferAntiFraudDto dto) {
        final AbstractSuspiciousTransferDto suspiciousTransferDto =
                suspiciousTransferDtoFactory.createSuspiciousTransferDto(dto.getEntityType());
        suspiciousTransferDto.setTransferId(dto.getTransferId());
        suspiciousTransferDto.setAmount(dto.getAmount());

        if (dto.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            suspiciousTransferDto.setIsBlocked(true);
            suspiciousTransferDto.setIsSuspicious(true);
            suspiciousTransferDto.setBlockedReason("High amount");
            suspiciousTransferDto.setSuspiciousReason("HIGH AMOUNT");
        } else {
            suspiciousTransferDto.setSuspiciousReason("Some reason");
            suspiciousTransferDto.setIsBlocked(false);
            suspiciousTransferDto.setIsSuspicious(false);
        }
        return suspiciousTransferDto;
    }

    private TransferAntiFraudDto toTransferAntiFraudDto(AbstractSuspiciousTransferDto suspiciousTransferDto) {
        final TransferAntiFraudDto kafkaDto = new TransferAntiFraudDto();
        kafkaDto.setTransferId(suspiciousTransferDto.getTransferId());
        kafkaDto.setAmount(suspiciousTransferDto.getAmount());
        kafkaDto.setEntityType(suspiciousTransferDto.getEntityType());
        kafkaDto.setIsBlocked(suspiciousTransferDto.getIsBlocked());
        kafkaDto.setIsSuspicious(suspiciousTransferDto.getIsSuspicious());
        return kafkaDto;
    }

    private void validateExistsInDb(TransferAntiFraudDto kafkaDto,
                                     JpaRepository<AbstractSuspiciousTransfer, Long> repository) {
        if (repository.existsById(kafkaDto.getTransferId())) {
            throw new ValidationException("Transfer with ID " +
                    kafkaDto.getTransferId() + " already exists in DB");
        }
    }
}
