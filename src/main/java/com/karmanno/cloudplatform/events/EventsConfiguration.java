package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@AutoConfigureAfter(KafkaAutoConfiguration.class)
@ConditionalOnProperty(value = "events.enabled", havingValue = "true")
public class EventsConfiguration {
    public static final String PUBLISHER_BEAN_FACTORY_NAME = "publisherFactory";
    public static final String PUBLISHER_CONTAINER_FACTORY_NAME = "eventPublisherContainerFactory";

    @Bean
    public EventsBeanDefinitionRegistryPostProcessor eventsBeanDefinitionFactoryPostProcessor(Environment environment) {
        return new EventsBeanDefinitionRegistryPostProcessor(environment);
    }

    @Bean
    public EventsBeanFactoryPostProcessor eventsBeanFactoryPostProcessor() {
        return new EventsBeanFactoryPostProcessor();
    }

    @Bean(name = PUBLISHER_BEAN_FACTORY_NAME)
    public EventPublisherBeanFactory publisherFactory(KafkaTemplate<String, String> kafkaTemplate,
                                                      ObjectMapper objectMapper) {
        return new EventPublisherBeanFactory(kafkaTemplate, objectMapper);
    }

    @Bean(name = PUBLISHER_CONTAINER_FACTORY_NAME)
    public EventPublisherContainerBeanFactory eventPublisherContainerFactory() {
        return new EventPublisherContainerBeanFactory();
    }
}
