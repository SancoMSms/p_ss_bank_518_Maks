package com.bank.antifraud.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.retries}")
    private Integer retries;

    @Value("${spring.kafka.max-poll-records}")
    private Integer maxPollRecords;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.auto-offset-reset-config}")
    private String autoOffsetResetConfig;

    @Value("${spring.kafka.asks-config}")
    private String asksConfig;

    @Bean
    public Map<String, Object> producerConfigs() {
        final Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, asksConfig);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        return props;
    }

    @Bean(name = "longStringKafkaTemplate")
    public KafkaTemplate<Long, String> longStringKafkaTemplate() {
        return createKafkaTemplate(LongSerializer.class, StringSerializer.class);
    }

    @Bean(name = "stringObjectKafkaTemplate")
    public KafkaTemplate<String, Object> stringObjectKafkaTemplate() {
        return createKafkaTemplate(StringSerializer.class, JsonSerializer.class);
    }

    private <K, V> KafkaTemplate<K, V> createKafkaTemplate(Class<?> keySerializer, Class<?> valueSerializer) {
        final Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        final ProducerFactory<K, V> factory = new DefaultKafkaProducerFactory<>(configProps);
        return new KafkaTemplate<>(factory);
    }
}
