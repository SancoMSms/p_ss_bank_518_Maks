package com.bank.profile.Config;

import com.bank.profile.DTO.ProfileDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = KafkaProfileDtoConfig.class)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=localhost:9092"
})
public class KafkaProfileDtoConfigTest {

    @Autowired
    private KafkaProfileDtoConfig config;

    @Test
    public void testProfileDtoProducerFactory() {
        ProducerFactory<String, ProfileDto> factory = config.profileDtoProducerFactory();
        assertNotNull(factory);

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

        ProducerFactory<String, ProfileDto> expectedFactory = config.profileDtoProducerFactory();
        assertEquals(expectedFactory.getConfigurationProperties(),
                kafkaTemplate.getProducerFactory().getConfigurationProperties());
    }
}
