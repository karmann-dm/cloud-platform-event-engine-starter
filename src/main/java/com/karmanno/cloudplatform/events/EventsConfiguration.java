package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@AutoConfigureAfter(KafkaAutoConfiguration.class)
@ConditionalOnProperty(value = "events.enabled", havingValue = "true")
public class EventsConfiguration {

    @Bean
    public EventPublishContainer eventPublishContainer(EventPublishConfigurer eventPublishConfigurer) {
        EventPublishContainer container = new EventPublishContainer();
        eventPublishConfigurer.configure(container);
        return container;
    }

    @Bean
    public EventPublishConfigurer eventPublishConfigurer(EventsProperties eventsProperties,
                                                         KafkaTemplate<String, String> kafkaTemplate,
                                                         ObjectMapper objectMapper) {
        return new EventPublishConfigurer(
                eventsProperties,
                kafkaTemplate,
                objectMapper
        );
    }
}
