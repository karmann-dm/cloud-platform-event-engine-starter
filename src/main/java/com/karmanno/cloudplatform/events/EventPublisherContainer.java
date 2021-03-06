package com.karmanno.cloudplatform.events;

import java.util.ArrayList;
import java.util.List;

public class EventPublisherContainer {
    private List<EventPublisher> eventPublishers = new ArrayList<>();

    public void setEventPublishers(List<EventPublisher> eventPublishers) {
        this.eventPublishers = eventPublishers;
    }

    public void addPublisher(EventPublisher eventPublisher) {
        this.eventPublishers.add(eventPublisher);
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
