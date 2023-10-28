package com.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    public static final String CATS_TOPIC = "cats";
    public static final String DOGS_TOPIC = "dogs";
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String address;
    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    NewTopic createCatsTopic(){
        return TopicBuilder.name(CATS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    NewTopic createDogsTopic(){
        return TopicBuilder.name(DOGS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, address);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory
    ){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

//    @Bean
//    StringCa createTemplate(ProducerFactory<String, String> factory){
//        return new KafkaTemplate<>(factory);
//    }
}
