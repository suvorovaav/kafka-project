package com.kafka.service;

import com.kafka.config.KafkaProducerConfig;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProducerService {

    KafkaTemplate<String, String> template;
    public void sendCatMessage(String message, int partition) {
        var future = template.send(KafkaProducerConfig.CATS_TOPIC, partition,"key1", message);
    }
    public void sendDogMessage(String message) {
        var future = template.send(KafkaProducerConfig.DOGS_TOPIC, message);
    }
}
