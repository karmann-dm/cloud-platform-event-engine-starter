package com.karmanno.cloudplatform.events;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class EventPublishContainer {
    private Map<Class<?>, EventPublisher> transferEventPublisherMap;

    public void publishEvent(Event event) {
        transferEventPublisherMap.get(event.getClass()).publish(event);
    }

    void insertPublisher(Class<?> eventClass, EventPublisher eventPublisher) {
        this.transferEventPublisherMap.put(eventClass, eventPublisher);
    }
}
