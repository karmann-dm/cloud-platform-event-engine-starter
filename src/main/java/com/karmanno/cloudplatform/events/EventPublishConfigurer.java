package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublishConfigurer {
    private final EventsProperties eventsProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void configure(EventPublishContainer container) {
        eventsProperties.getTopics().forEach((topic, cls) -> {
            if (!cls.isAssignableFrom(Event.class))
                throw new RuntimeException("Class " + cls + " does not implement Event class");
            container.insertPublisher(cls, event ->
                    kafkaTemplate.send(topic, objectMapper.writeValueAsString(event))
            );
        });
    }
}
