package com.bank.antifraud.services;

import com.bank.antifraud.dto.*;
import com.bank.antifraud.dto.SuspiciousTransferDto;
import com.bank.antifraud.entities.*;
import com.bank.antifraud.exception.ValidationException;
import com.bank.antifraud.mappers.SuspiciousTransferMapper;
import com.bank.antifraud.kafka.producer.SuspiciousTransferProducer;
import com.bank.antifraud.repositories.*;
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

    private static final Logger logger = LoggerFactory.getLogger(SuspiciousTransferServiceImpl.class);

    private final SuspiciousTransferRepositoryManager repositoryManager;
    private final SuspiciousTransferDtoFactory suspiciousTransferDtoFactory;
    private final SuspiciousTransferProducer suspiciousTransferProducer;
    private final SuspiciousTransferMapper mapper;

    @Override
    public SuspiciousTransferDto createSuspiciousTransfer(TransferAntiFraudDto kafkaDto) {
        validateInput(kafkaDto);

        logger.info("Creating suspicious transfer of type: {}", kafkaDto.getEntityType());
        SuspiciousTransferDto result = processTransfer(toSuspiciousTransferDto(kafkaDto));
        suspiciousTransferProducer.sendCreateEvent(kafkaDto);
        logger.info("Suspicious transfer created and sent to Kafka");
        return result;
    }

    @Override
    @Transactional
    public SuspiciousTransferDto updateSuspiciousTransfer(TransferAntiFraudDto kafkaDto) {
        validateInput(kafkaDto);

        JpaRepository repository = repositoryManager.getRepository(kafkaDto.getEntityType());

        SuspiciousTransferDto transferDto = toSuspiciousTransferDto(kafkaDto);
        logger.info("Updating suspicious transferDto: {}", transferDto);

        if (!repository.existsById(kafkaDto.getTransferId())) {
            throw new ValidationException("Suspicious transfer with ID " + kafkaDto.getTransferId() + " does not exist");
        }
        SuspiciousTransfer entity = mapper.toEntity(transferDto);
        SuspiciousTransfer savedEntity = (SuspiciousTransfer) repository.save(entity);
        SuspiciousTransferDto updatedTransferDto = mapper.toDto(savedEntity);
        TransferAntiFraudDto updatedKafkaDto = toTransferAntiFraudDto(updatedTransferDto);
        suspiciousTransferProducer.sendUpdateEvent(updatedKafkaDto);
        logger.info("Suspicious transfer updated and sent to Kafka");
        return updatedTransferDto;
    }

    @Override
    @Transactional
    public void deleteSuspiciousTransfer(TransferAntiFraudDto kafkaDto) {
        validateInput(kafkaDto);

        Long transferId = kafkaDto.getTransferId();
        JpaRepository repository = repositoryManager.getRepository(kafkaDto.getEntityType());
        if (!repository.existsById(transferId)) {
            throw new ValidationException("Suspicious transfer with ID " + kafkaDto.getTransferId() + " does not exist");
        }
        repository.deleteById(transferId);
        suspiciousTransferProducer.sendDeleteEvent(kafkaDto);
        logger.info("Suspicious transfer deleted and sent to Kafka");
    }

    public SuspiciousTransferDto processTransfer(SuspiciousTransferDto transferDto) { //TODO убрать, тк используется только в одном месте

        JpaRepository repository = repositoryManager.getRepository(transferDto.getEntityType());
        SuspiciousTransfer entity = mapper.toEntity(transferDto);
        SuspiciousTransfer saved = (SuspiciousTransfer) repository.save(entity);
        transferDto.setId(saved.getId());
        return transferDto;
    }

    private SuspiciousTransferDto toSuspiciousTransferDto(TransferAntiFraudDto kafkaDto){

        SuspiciousTransferDto suspiciousTransferDto = suspiciousTransferDtoFactory.createSuspiciousTransferDto(kafkaDto.getEntityType());
        //suspiciousTransferDto.setId(kafkaDto.getTransferId());
        suspiciousTransferDto.setTransferId(kafkaDto.getTransferId());
        suspiciousTransferDto.setAmount(kafkaDto.getAmount());

        if (kafkaDto.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            suspiciousTransferDto.setIs_blocked(true);
            suspiciousTransferDto.setIs_suspicious(true);
            suspiciousTransferDto.setBlocked_reason("High amount");
            suspiciousTransferDto.setSuspicious_reason("HIGH AMOUNT");
            //suspiciousTransferDto.setModified_by("anti_fraud"); //TODO разобраться как передать эту инфу в аудит
        } else {
            suspiciousTransferDto.setSuspicious_reason("Some reason");
            suspiciousTransferDto.setIs_blocked(false);
            suspiciousTransferDto.setIs_suspicious(false);
        }
        return suspiciousTransferDto;
    }

    private TransferAntiFraudDto toTransferAntiFraudDto(SuspiciousTransferDto suspiciousTransferDto) {

        TransferAntiFraudDto kafkaDto = new TransferAntiFraudDto();
        kafkaDto.setTransferId(suspiciousTransferDto.getTransferId());
        kafkaDto.setAmount(suspiciousTransferDto.getAmount());
        kafkaDto.setEntityType(suspiciousTransferDto.getEntityType());
        kafkaDto.setIsBlocked(suspiciousTransferDto.getIs_blocked());
        kafkaDto.setIsSuspicious(suspiciousTransferDto.getIs_suspicious());
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
}