package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

@Configuration
@AutoConfigureAfter(KafkaAutoConfiguration.class)
@ConditionalOnProperty(value = "events.enabled", havingValue = "true")
public class EventsConfiguration {
    public static final String PUBLISHER_BEAN_FACTORY_NAME = "publisherFactory";

    @Bean
    public EventsBeanDefinitionFactoryPostProcessor eventsBeanDefinitionFactoryPostProcessor(Environment environment) {
        return new EventsBeanDefinitionFactoryPostProcessor(environment);
    }

    @Bean(name = PUBLISHER_BEAN_FACTORY_NAME)
    public EventPublisherBeanFactory publisherFactory(KafkaTemplate<String, String> kafkaTemplate,
                                                      ObjectMapper objectMapper) {
        return new EventPublisherBeanFactory(kafkaTemplate, objectMapper);
    }

    @Bean
    public EventPublisherContainer eventPublisher(List<EventPublisher> eventPublishers) {
        return new EventPublisherContainer(eventPublishers);
    }
}
