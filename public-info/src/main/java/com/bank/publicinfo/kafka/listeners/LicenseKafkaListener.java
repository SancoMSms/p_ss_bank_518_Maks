package com.bank.publicinfo.kafka.listeners;

import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.kafka.producers.LicenseKafkaProducer;
import com.bank.publicinfo.service.LicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.bank.publicinfo.util.IdParserUtil.parseId;

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseKafkaListener {

    private final LicenseService licenseService;
    private final LicenseKafkaProducer kafkaProducer;

    @Value("${spring.kafka.topics.license.create.response}")
    private String createResponseTopic;

    @Value("${spring.kafka.topics.license.update.response}")
    private String updateResponseTopic;

    @Value("${spring.kafka.topics.license.delete.response}")
    private String deleteResponseTopic;

    @Value("${spring.kafka.topics.license.get.response}")
    private String getResponseTopic;

    @KafkaListener(
            topics = "${spring.kafka.topics.license.create.name}",
            groupId = "${spring.kafka.groups.license}",
            containerFactory = "licenseKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenCreate(LicenseDto licenseDto) {
        if (licenseDto == null) {
            throw new IllegalArgumentException("LicenseDto is null");
        }
        log.info("Processing create license: {}", licenseDto);
        LicenseDto created = licenseService.addLicense(licenseDto);
        kafkaProducer.sendMessage(createResponseTopic, created);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.license.update.name}",
            groupId = "${spring.kafka.groups.license}",
            containerFactory = "licenseKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenUpdate(LicenseDto licenseDto) {
        if (licenseDto == null) {
            throw new IllegalArgumentException("LicenseDto is null");
        }
        log.info("Processing update license: {}", licenseDto);
        LicenseDto updated = licenseService.updateLicense(licenseDto);
        kafkaProducer.sendMessage(updateResponseTopic, updated);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.license.delete.name}",
            groupId = "${spring.kafka.groups.license}",
            containerFactory = "licenseKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenDelete(String message) {
        Long licenseId = parseId(message, "license");
        log.info("Processing delete for license id: {}", licenseId);
        licenseService.deleteLicense(licenseId);
        kafkaProducer.sendMessage(deleteResponseTopic, "Deleted license with id: " + licenseId);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.license.get.name}",
            groupId = "${spring.kafka.groups.license}",
            containerFactory = "licenseKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenGet(String message) {
        Long licenseId = parseId(message, "license");
        log.info("Processing get request for license id: {}", licenseId);
        LicenseDto licenseDto = licenseService.getLicenseById(licenseId);
        kafkaProducer.sendMessage(getResponseTopic, licenseDto);
    }

}


