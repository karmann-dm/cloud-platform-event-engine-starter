package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;

public class EventPublishConfigurer {
    private final EventsProperties eventsProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public EventPublishConfigurer(EventsProperties eventsProperties,
                                  KafkaTemplate<String, String> kafkaTemplate,
                                  ObjectMapper objectMapper) {
        this.eventsProperties = eventsProperties;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void configure(EventPublishContainer container) {
        eventsProperties.getTopics().forEach((topic, cls) -> {
            if (!cls.isAssignableFrom(Event.class))
                throw new RuntimeException("Class " + cls + " does not implement Event class");
            container.insertPublisher(cls, event -> {
                        try {
                            kafkaTemplate.send(topic, objectMapper.writeValueAsString(event));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });
    }
}
