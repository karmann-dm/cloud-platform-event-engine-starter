package com.karmanno.cloudplatform.events;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
