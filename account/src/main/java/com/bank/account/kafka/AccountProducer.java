package com.bank.account.kafka;

import com.bank.account.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountProducer {

    private static final String TOPIC_CREATE = "account.create";
    private static final String TOPIC_UPDATE = "account.update";
    private static final String TOPIC_DELETE = "account.delete";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCreateEvent(AccountDto accountDto) {
        kafkaTemplate.send(TOPIC_CREATE, accountDto);
    }

    public void sendUpdateEvent(AccountDto accountDto) {
        kafkaTemplate.send(TOPIC_UPDATE, accountDto);
    }

    public void sendDeleteEvent(Long accountId) {
        kafkaTemplate.send(TOPIC_DELETE, accountId);
    }
}
