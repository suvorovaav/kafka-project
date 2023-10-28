package com.kafka.service;

import com.kafka.config.KafkaConsumerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    KafkaTemplate<String, String> template;

    @KafkaListener(topics = {KafkaConsumerConfig.CATS_TOPIC, KafkaConsumerConfig.DOGS_TOPIC},
            topicPartitions = @TopicPartition(topic = KafkaConsumerConfig.CATS_TOPIC,
                    partitions = { "0", "1" }))
    public void listenCats(@Header(KafkaHeaders.OFFSET) Long offset, String message) {
        System.out.println("Message received: offset = " + offset + "; message = " + message);
    }
}
