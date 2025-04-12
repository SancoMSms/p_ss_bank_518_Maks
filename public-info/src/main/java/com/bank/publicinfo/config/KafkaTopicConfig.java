package com.bank.publicinfo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    private NewTopic topic(String name) {
        return new NewTopic(name, 1, (short) 1);
    }

    @Bean
    public NewTopic createBankTopic() {
        return topic("public-info.bank.create");
    }

    @Bean
    public NewTopic updateBankTopic() {
        return topic("public-info.bank.update");
    }

    @Bean
    public NewTopic deleteBankTopic() {
        return topic("public-info.bank.delete");
    }

    @Bean
    public NewTopic getBankTopic() {
        return topic("public-info.bank.get");
    }

    @Bean
    public NewTopic createBranchTopic() {
        return topic("public-info.branch.create");
    }

    @Bean
    public NewTopic updateBranchTopic() {
        return topic("public-info.branch.update");
    }

    @Bean
    public NewTopic deleteBranchTopic() {
        return topic("public-info.branch.delete");
    }

    @Bean
    public NewTopic getBranchTopic() {
        return topic("public-info.branch.get");
    }

    @Bean
    public NewTopic createAtmTopic() {
        return topic("public-info.atm.create");
    }

    @Bean
    public NewTopic updateAtmTopic() {
        return topic("public-info.atm.update");
    }

    @Bean
    public NewTopic deleteAtmTopic() {
        return topic("public-info.atm.delete");
    }

    @Bean
    public NewTopic getAtmTopic() {
        return topic("public-info.atm.get");
    }

    @Bean
    public NewTopic createLicenseTopic() {
        return topic("public-info.license.create");
    }

    @Bean
    public NewTopic updateLicenseTopic() {
        return topic("public-info.license.update");
    }

    @Bean
    public NewTopic deleteLicenseTopic() {
        return topic("public-info.license.delete");
    }

    @Bean
    public NewTopic getLicenseTopic() {
        return topic("public-info.license.get");
    }

    @Bean
    public NewTopic createCertificateTopic() {
        return topic("public-info.certificate.create");
    }

    @Bean
    public NewTopic updateCertificateTopic() {
        return topic("public-info.certificate.update");
    }

    @Bean
    public NewTopic deleteCertificateTopic() {
        return topic("public-info.certificate.delete");
    }

    @Bean
    public NewTopic getCertificateTopic() {
        return topic("public-info.certificate.get");
    }

    @Bean
    public NewTopic createAuditTopic() {
        return topic("public-info.audit.create");
    }

    @Bean
    public NewTopic updateAuditTopic() {
        return topic("public-info.audit.update");
    }

    @Bean
    public NewTopic deleteAuditTopic() {
        return topic("public-info.audit.delete");
    }

    @Bean
    public NewTopic getAuditTopic() {
        return topic("public-info.audit.get");
    }
}

