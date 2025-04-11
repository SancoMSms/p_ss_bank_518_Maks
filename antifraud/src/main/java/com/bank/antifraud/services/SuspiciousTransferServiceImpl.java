package com.bank.antifraud.services;

import com.bank.antifraud.dto.AbstractSuspiciousTransferDto;
import com.bank.antifraud.dto.SuspiciousTransferDtoFactory;
import com.bank.antifraud.dto.TransferAntiFraudDto;
import com.bank.antifraud.entities.AbstractSuspiciousTransfer;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.mappers.SuspiciousTransferMapper;
import com.bank.antifraud.kafka.producer.SuspiciousTransferProducer;
import com.bank.antifraud.repositories.SuspiciousTransferRepositoryManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SuspiciousTransferServiceImpl implements SuspiciousTransferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuspiciousTransferServiceImpl.class);

    private final SuspiciousTransferRepositoryManager repositoryManager;
    private final SuspiciousTransferDtoFactory suspiciousTransferDtoFactory;
    private final SuspiciousTransferProducer suspiciousTransferProducer;
    private final SuspiciousTransferMapper mapper;

    @Override
    public AbstractSuspiciousTransferDto createSuspiciousTransfer(TransferAntiFraudDto kafkaDto) {
        validateInput(kafkaDto);
        LOGGER.info("Creating suspicious transfer of type: {}", kafkaDto.getEntityType());
        final JpaRepository<AbstractSuspiciousTransfer, Long> repository =
                repositoryManager.getRepository(kafkaDto.getEntityType());
        if (repository.existsById(kafkaDto.getTransferId())) {
            throw new ValidationException("Transfer with ID " +
                    kafkaDto.getTransferId() + " already exists in DB");
        }
        final AbstractSuspiciousTransferDto transferDto = toSuspiciousTransferDto(kafkaDto);
        final AbstractSuspiciousTransfer entity = mapper.toEntity(transferDto);
        entity.syncIdWithTransferId();
        final AbstractSuspiciousTransfer savedEntity = repository.save(entity);
        final AbstractSuspiciousTransferDto savedDto = mapper.toDto(savedEntity);
        final TransferAntiFraudDto savedKafkaDto = toTransferAntiFraudDto(savedDto);
        suspiciousTransferProducer.sendCreateEvent(savedKafkaDto);
        LOGGER.info("Suspicious transfer created and sent to Kafka");
        return savedDto;
    }

    @Override
    @Transactional
    public AbstractSuspiciousTransferDto updateSuspiciousTransfer(TransferAntiFraudDto kafkaDto) {
        validateInput(kafkaDto);
        final JpaRepository<AbstractSuspiciousTransfer, Long> repository =
                repositoryManager.getRepository(kafkaDto.getEntityType());
        validateExistsInDb(kafkaDto.getTransferId(), repository);
        final AbstractSuspiciousTransferDto transferDto = toSuspiciousTransferDto(kafkaDto);
        LOGGER.info("Updating suspicious transferDto: {}", transferDto);
        final AbstractSuspiciousTransfer entity =
                mapper.toEntity(transferDto);
        entity.syncIdWithTransferId();
        final AbstractSuspiciousTransfer savedEntity = repository.save(entity);
        final AbstractSuspiciousTransferDto updatedTransferDto = mapper.toDto(savedEntity);
        final TransferAntiFraudDto updatedKafkaDto =
                toTransferAntiFraudDto(updatedTransferDto);
        suspiciousTransferProducer.sendUpdateEvent(updatedKafkaDto);
        LOGGER.info("Suspicious transfer updated and sent to Kafka");
        return updatedTransferDto;
    }

    @Override
    @Transactional
    public void deleteSuspiciousTransfer(TransferAntiFraudDto kafkaDto) {
        validateInput(kafkaDto);
        final Long transferId = kafkaDto.getTransferId();
        final JpaRepository<AbstractSuspiciousTransfer, Long> repository =
                repositoryManager.getRepository(kafkaDto.getEntityType());
        validateExistsInDb(transferId, repository);
        repository.deleteById(transferId);
        suspiciousTransferProducer.sendDeleteEvent(kafkaDto);
        LOGGER.info("Suspicious transfer deleted and sent to Kafka");
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

    private void validateInput(TransferAntiFraudDto kafkaDto) {
        if (kafkaDto == null) {
            throw new ValidationException("Kafka DTO cannot be null");
        }
        if (kafkaDto.getEntityType() == null || kafkaDto.getEntityType().isEmpty()) {
            throw new ValidationException("Entity type in Kafka DTO cannot be null or empty");
        }
        if (kafkaDto.getTransferId() == null) {
            throw new ValidationException("Transfer ID in Kafka DTO cannot be null");
        }
        if (kafkaDto.getAmount() == null) {
            throw new ValidationException("Amount in Kafka DTO cannot be null");
        }
    }

    private void validateExistsInDb(Long transferId,
                                    JpaRepository<AbstractSuspiciousTransfer, Long> repository) {
        if (!repository.existsById(transferId)) {
            throw new ValidationException("Suspicious transfer with ID " +
                    transferId + " does not exist");
        }
    }
}
