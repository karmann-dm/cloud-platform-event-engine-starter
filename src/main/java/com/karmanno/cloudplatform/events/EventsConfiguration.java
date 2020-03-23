package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(value = "events.enabled", havingValue = "true")
public class EventsConfiguration {
    public static final String PUBLISHER_BEAN_FACTORY_NAME = "publisherFactory";

    @Bean
    @ConfigurationProperties(prefix = "events")
    public EventsProperties eventsProperties() {
        return new EventsProperties();
    }

    @Bean
    public EventsBeanDefinitionRegistryPostProcessor eventsBeanDefinitionFactoryPostProcessor(Environment environment) {
        return new EventsBeanDefinitionRegistryPostProcessor(environment);
    }

    @Bean
    public EventPublisherBeanPostProcessor eventPublisherBeanPostProcessor(ApplicationContext applicationContext) {
        EventPublisherBeanPostProcessor eventPublisherBeanPostProcessor = new EventPublisherBeanPostProcessor();
        eventPublisherBeanPostProcessor.setApplicationContext(applicationContext);
        return eventPublisherBeanPostProcessor;
    }

    @Bean(name = PUBLISHER_BEAN_FACTORY_NAME)
    public EventPublisherBeanFactory publisherFactory(KafkaTemplate<String, String> kafkaTemplate,
                                                      ObjectMapper objectMapper) {
        return new EventPublisherBeanFactory(kafkaTemplate, objectMapper);
    }

}
