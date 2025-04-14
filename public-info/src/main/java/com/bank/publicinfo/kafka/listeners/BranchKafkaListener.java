package com.bank.publicinfo.kafka.listeners;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.kafka.producers.BranchKafkaProducer;
import com.bank.publicinfo.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.bank.publicinfo.util.IdParserUtil.parseId;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchKafkaListener {

    private final BranchService branchService;
    private final BranchKafkaProducer kafkaProducer;

    @Value("${spring.kafka.topics.branch.create.response}")
    private String createResponseTopic;

    @Value("${spring.kafka.topics.branch.update.response}")
    private String updateResponseTopic;

    @Value("${spring.kafka.topics.branch.delete.response}")
    private String deleteResponseTopic;

    @Value("${spring.kafka.topics.branch.get.response}")
    private String getResponseTopic;

    @KafkaListener(
            topics = "${spring.kafka.topics.branch.create.name}",
            groupId = "${spring.kafka.groups.branch}",
            containerFactory = "branchKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenCreate(BranchDto branchDto) {
        if (branchDto == null) {
            throw new IllegalArgumentException("BranchDto is null");
        }
        BranchDto created = branchService.addBranch(branchDto);
        kafkaProducer.sendMessage(createResponseTopic, created);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.branch.update.name}",
            groupId = "${spring.kafka.groups.branch}",
            containerFactory = "branchKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenUpdate(BranchDto branchDto) {
        if (branchDto == null) {
            throw new IllegalArgumentException("BranchDto is null");
        }
        BranchDto updated = branchService.updateBranch(branchDto);
        kafkaProducer.sendMessage(updateResponseTopic, updated);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.branch.delete.name}",
            groupId = "${spring.kafka.groups.branch}",
            containerFactory = "branchKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenDelete(String message) {
        Long branchId = parseId(message, "branch");
        branchService.deleteBranch(branchId);
        kafkaProducer.sendMessage(deleteResponseTopic, "Deleted branch with id: " + branchId);
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.branch.get.name}",
            groupId = "${spring.kafka.groups.branch}",
            containerFactory = "branchKafkaListenerContainerFactory",
            errorHandler = "globalExceptionHandler"
    )
    public void listenGet(String message) {
        Long branchId = parseId(message, "branch");
        BranchDto branchDto = branchService.getBranchById(branchId);
        kafkaProducer.sendMessage(getResponseTopic, branchDto);
    }

}


