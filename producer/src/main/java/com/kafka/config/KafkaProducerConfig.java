package com.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    public static final String CATS_TOPIC = "cats";
    public static final String DOGS_TOPIC = "dogs";
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String address;

    @Bean
    NewTopic createTopic(){
        return TopicBuilder.name(CATS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    NewTopic createSecondTopic(){
        return TopicBuilder.name(DOGS_TOPIC)
                .replicas(1)
                .build();
    }
    @Bean
    ProducerFactory<String, String>  producerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 3);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    KafkaTemplate<String, String> createTemplate(ProducerFactory<String, String> factory){
        return new KafkaTemplate<>(factory);
    }

}
