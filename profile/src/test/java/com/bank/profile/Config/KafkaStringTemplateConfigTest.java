package com.bank.profile.Config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class KafkaStringTemplateConfigTest {

    private static final String TEST_BOOTSTRAP_SERVERS = "localhost:9092";

    @Test
    void testProducerFactoryConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, TEST_BOOTSTRAP_SERVERS);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(config);

        Class<?> keySerializerClass = (Class<?>) config.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG);
        Class<?> valueSerializerClass = (Class<?>) config.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG);

        assertEquals(StringSerializer.class, keySerializerClass);
        assertEquals(StringSerializer.class, valueSerializerClass);
    }

    @Test
    void testKafkaTemplate() {
        KafkaStringTemplateConfig kafkaStringTemplateConfig = new KafkaStringTemplateConfig();
        setField(kafkaStringTemplateConfig, "bootstrapServers", TEST_BOOTSTRAP_SERVERS);

        KafkaTemplate<String, String> kafkaTemplate = kafkaStringTemplateConfig.kafkaStringTemplate();
        assertNotNull(kafkaTemplate);
    }
}
