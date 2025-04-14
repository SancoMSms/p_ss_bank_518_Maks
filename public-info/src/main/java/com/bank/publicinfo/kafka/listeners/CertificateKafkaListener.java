package com.bank.publicinfo.kafka.listeners;

import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.kafka.producers.CertificateKafkaProducer;
import com.bank.publicinfo.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.bank.publicinfo.util.IdParserUtil.parseId;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateKafkaListener {

    private final CertificateService certificateService;
    private final CertificateKafkaProducer kafkaProducer;

    @Value("${spring.kafka.topics.certificate.create.response}")
    private String createResponseTopic;

    @Value("${spring.kafka.topics.certificate.update.response}")
    private String updateResponseTopic;

    @Value("${spring.kafka.topics.certificate.delete.response}")
    private String deleteResponseTopic;

    @Value("${spring.kafka.topics.certificate.get.response}")
    private String getResponseTopic;

    @KafkaListener(
            topics = "${spring.kafka.topics.certificate.create.name}",
            groupId = "${spring.kafka.groups.certificate}",
            containerFactory = "certificateKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenCreate(CertificateDto certificateDto) {
        if (certificateDto == null) {
            throw new IllegalArgumentException("CertificateDto is null");
        }
        log.info("Processing create certificate: {}", certificateDto);
        CertificateDto created = certificateService.addCertificate(certificateDto);
        kafkaProducer.sendMessage(createResponseTopic, created);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.certificate.update.name}",
            groupId = "${spring.kafka.groups.certificate}",
            containerFactory = "certificateKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenUpdate(CertificateDto certificateDto) {
        if (certificateDto == null) {
            throw new IllegalArgumentException("CertificateDto is null");
        }
        log.info("Processing update certificate: {}", certificateDto);
        CertificateDto updated = certificateService.updateCertificate(certificateDto);
        kafkaProducer.sendMessage(updateResponseTopic, updated);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.certificate.delete.name}",
            groupId = "${spring.kafka.groups.certificate}",
            containerFactory = "certificateKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenDelete(String message) {
        Long certificateId = parseId(message, "certificate");
        log.info("Processing delete for certificate id: {}", certificateId);
        certificateService.deleteCertificate(certificateId);
        kafkaProducer.sendMessage(deleteResponseTopic, "Deleted certificate with id: " + certificateId);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.certificate.get.name}",
            groupId = "${spring.kafka.groups.certificate}",
            containerFactory = "certificateKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenGet(String message) {
        Long certificateId = parseId(message, "certificate");
        log.info("Processing get request for certificate id: {}", certificateId);
        CertificateDto certificateDto = certificateService.getCertificateById(certificateId);
        kafkaProducer.sendMessage(getResponseTopic, certificateDto);
    }

}

