package com.bank.publicinfo.kafka.listeners;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.kafka.producers.BankKafkaProducer;
import com.bank.publicinfo.service.BankDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.bank.publicinfo.util.IdParserUtil.parseId;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankKafkaListener {

    private final BankDetailsService bankDetailsService;
    private final BankKafkaProducer kafkaProducer;

    @Value("${spring.kafka.topics.bank.create.response}")
    private String createResponseTopic;

    @Value("${spring.kafka.topics.bank.update.response}")
    private String updateResponseTopic;

    @Value("${spring.kafka.topics.bank.delete.response}")
    private String deleteResponseTopic;

    @Value("${spring.kafka.topics.bank.get.response}")
    private String getResponseTopic;

    @KafkaListener(
            topics = "${spring.kafka.topics.bank.create.name}",
            groupId = "${spring.kafka.groups.bank}",
            containerFactory = "bankKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenCreate(BankDetailsDto bankDetailsDto) {
        if (bankDetailsDto == null) {
            throw new IllegalArgumentException("BankDetailsDto is null");
        }
        BankDetailsDto created = bankDetailsService.addBankDetails(bankDetailsDto);
        kafkaProducer.sendMessage(createResponseTopic, created);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.bank.update.name}",
            groupId = "${spring.kafka.groups.bank}",
            containerFactory = "bankKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenUpdate(BankDetailsDto bankDetailsDto) {
        if (bankDetailsDto == null) {
            throw new IllegalArgumentException("BankDetailsDto is null");
        }
        BankDetailsDto updated = bankDetailsService.updateBankDetails(bankDetailsDto);
        kafkaProducer.sendMessage(updateResponseTopic, updated);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.bank.delete.name}",
            groupId = "${spring.kafka.groups.bank}",
            containerFactory = "bankKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenDelete(String message) {
        Long bankId = parseId(message, "bank");
        bankDetailsService.deleteBankDetails(bankId);
        kafkaProducer.sendMessage(deleteResponseTopic, "Deleted bank with id: " + bankId);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.bank.get.name}",
            groupId = "${spring.kafka.groups.bank}",
            containerFactory = "bankKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenGet(String message) {
        Long bankId = parseId(message, "bank");
        BankDetailsDto bankDetailsDto = bankDetailsService.getBankDetailsById(bankId);
        kafkaProducer.sendMessage(getResponseTopic, bankDetailsDto);
    }

}



