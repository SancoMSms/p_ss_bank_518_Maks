package com.bank.profile.Config;

import com.bank.profile.DTO.ProfileDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KafkaProfileDtoConfigTest {

    private KafkaProfileDtoConfig config;

    @BeforeEach
    public void setUp() {
        config = new KafkaProfileDtoConfig();
    }

    @Test
    public void testProfileDtoProducerFactory() {
        ProducerFactory<String, ProfileDto> factory = config.profileDtoProducerFactory();
        assertNotNull(factory);

        // Проверим содержимое настроек
        Map<String, Object> configProps = factory.getConfigurationProperties();
        assertEquals("localhost:9092", configProps.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(StringSerializer.class, configProps.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        assertEquals(JsonSerializer.class, configProps.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
        assertEquals(false, configProps.get(JsonSerializer.ADD_TYPE_INFO_HEADERS));
    }

    @Test
    public void testProfileDtoKafkaTemplate() {
        KafkaTemplate<String, ProfileDto> kafkaTemplate = config.profileDtoKafkaTemplate();
        assertNotNull(kafkaTemplate);

        // Проверим, что template использует фабрику
        assertNotNull(kafkaTemplate.getProducerFactory());
    }
}
