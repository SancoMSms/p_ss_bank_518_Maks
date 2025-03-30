package com.bank.profile.Services;

import com.bank.profile.Entities.AccountDetails;
import com.bank.profile.Services.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_CREATE = "profile.create";
    private static final String TOPIC_UPDATE = "profile.update";
    private static final String TOPIC_DELETE = "profile.delete";

    private final Map<Long, AccountDetails> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public AccountDetails createAccountDetails(AccountDetails accountDetails) {
        Long id = idGenerator.getAndIncrement();
        accountDetails.setId(id);
        storage.put(id, accountDetails);
        kafkaTemplate.send(TOPIC_CREATE, "Created AccountDetails ID: " + id);
        return accountDetails;
    }

    @Override
    public Optional<AccountDetails> getAccountDetailsById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<AccountDetails> getAllAccountDetails() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public AccountDetails updateAccountDetails(Long id, AccountDetails accountDetails) {
        if (!storage.containsKey(id)) {
            throw new RuntimeException("AccountDetails not found with id " + id);
        }
        accountDetails.setId(id);
        storage.put(id, accountDetails);
        kafkaTemplate.send(TOPIC_UPDATE, "Updated AccountDetails ID: " + id);
        return accountDetails;
    }

    @Override
    public void deleteAccountDetails(Long id) {
        if (!storage.containsKey(id)) {
            throw new RuntimeException("AccountDetails not found with id " + id);
        }
        storage.remove(id);
        kafkaTemplate.send(TOPIC_DELETE, "Deleted AccountDetails ID: " + id);
    }
}
