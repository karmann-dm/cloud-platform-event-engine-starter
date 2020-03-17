package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class EventPublisherBeanFactory {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static class EventTransfer {
        private String id;
        private String emitter;
        private String timestamp;
        private String body;

        public EventTransfer(String id, String emitter, String timestamp, String body) {
            this.id = id;
            this.emitter = emitter;
            this.timestamp = timestamp;
            this.body = body;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getEmitter() {
            return emitter;
        }

        public void setEmitter(String emitter) {
            this.emitter = emitter;
        }
    }

    public EventPublisherBeanFactory(KafkaTemplate<String, String> kafkaTemplate,
                                     ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public Object create(Class<?> eventClass, String topic) {
        return new EventPublisher() {
            private Logger logger = LoggerFactory.getLogger(this.getClass());

            @Override
            public Class<?> getAcceptableTransferClass() {
                if (!Event.class.isAssignableFrom(eventClass))
                    throw new RuntimeException("Class " + eventClass + "should implement Event class");
                return eventClass;
            }

            @Override
            public void publish(Event event) {
                try {
                    kafkaTemplate.send(topic, objectMapper.writeValueAsString(
                            new EventTransfer(
                                    event.getId(),
                                    event.getEmitter(),
                                    event.getTimestamp().toString(),
                                    event.getRepresentation().get()
                            )
                    ));
                } catch (Exception e) {
                    logger.error("Error while sending event to Kafka", e);
                }
            }
        };
    }
}
