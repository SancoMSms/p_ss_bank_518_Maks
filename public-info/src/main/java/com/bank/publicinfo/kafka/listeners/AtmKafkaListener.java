package com.bank.publicinfo.kafka.listeners;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.kafka.producers.AtmKafkaProducer;
import com.bank.publicinfo.service.AtmService;
import com.bank.publicinfo.util.IdParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AtmKafkaListener {

    private final AtmService atmService;
    private final AtmKafkaProducer kafkaProducer;

    @Value("${spring.kafka.topics.atm.create.response}")
    private String createResponseTopic;

    @Value("${spring.kafka.topics.atm.update.response}")
    private String updateResponseTopic;

    @Value("${spring.kafka.topics.atm.delete.response}")
    private String deleteResponseTopic;

    @Value("${spring.kafka.topics.atm.get.response}")
    private String getResponseTopic;

    @KafkaListener(
            topics = "${spring.kafka.topics.atm.create.name}",
            groupId = "${spring.kafka.groups.atm}",
            containerFactory = "atmKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenCreate(AtmDto atmDto) {
        if (atmDto == null) {
            throw new IllegalArgumentException("AtmDto is null");
        }
        AtmDto created = atmService.addAtm(atmDto);
        kafkaProducer.sendMessage(createResponseTopic, created);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.atm.update.name}",
            groupId = "${spring.kafka.groups.atm}",
            containerFactory = "atmKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenUpdate(AtmDto atmDto) {
        if (atmDto == null) {
            throw new IllegalArgumentException("AtmDto is null");
        }
        AtmDto updated = atmService.updateAtm(atmDto);
        kafkaProducer.sendMessage(updateResponseTopic, updated);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.atm.delete.name}",
            groupId = "${spring.kafka.groups.atm}",
            containerFactory = "atmKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenDelete(String message) {
        Long atmId = IdParserUtil.parseId(message, "atm");
        atmService.deleteAtm(atmId);
        kafkaProducer.sendMessage(deleteResponseTopic, "Deleted ATM with id: " + atmId);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.atm.get.name}",
            groupId = "${spring.kafka.groups.atm}",
            containerFactory = "atmKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenGet(String message) {
        Long atmId = IdParserUtil.parseId(message, "atm");
        AtmDto atmDto = atmService.getAtmById(atmId);
        kafkaProducer.sendMessage(getResponseTopic, atmDto);
    }
}


