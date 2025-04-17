package com.bank.profile.Config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class KafkaStringTemplateConfigTest {

    @Test
    void testProducerFactoryConfig() {
        String bootstrapServers = "localhost:9092";

        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(config);

        Class<?> keySerializerClass = (Class<?>) config.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG);
        Class<?> valueSerializerClass = (Class<?>) config.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG);

        assertTrue(keySerializerClass.equals(StringSerializer.class));
        assertTrue(valueSerializerClass.equals(StringSerializer.class));
    }

    @Test
    void testKafkaTemplate() {
        KafkaStringTemplateConfig kafkaStringTemplateConfig = new KafkaStringTemplateConfig();

        ReflectionTestUtils.setField(kafkaStringTemplateConfig, "bootstrapServers", "localhost:9092");

        KafkaTemplate<String, String> kafkaTemplate = kafkaStringTemplateConfig.kafkaStringTemplate();

        assertTrue(kafkaTemplate != null);
    }
}
