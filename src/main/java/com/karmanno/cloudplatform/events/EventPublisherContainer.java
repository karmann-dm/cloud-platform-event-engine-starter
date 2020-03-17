package com.karmanno.cloudplatform.events;

import java.util.List;

public class EventPublisherContainer {
    private final List<EventPublisher> eventPublishers;

    public EventPublisherContainer(List<EventPublisher> eventPublishers) {
        this.eventPublishers = eventPublishers;
    }

    public void publish(Event event) {
        EventPublisher p = eventPublishers.stream()
                .filter(publisher -> publisher.getAcceptableTransferClass().equals(event.getClass()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Publisher for event class " + event.getClass() + " not found")
                );
        p.publish(event);
    }
}
