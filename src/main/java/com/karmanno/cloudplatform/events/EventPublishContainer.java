package com.karmanno.cloudplatform.events;

import java.util.Map;

public class EventPublishContainer {
    private Map<Class<?>, EventPublisher> transferEventPublisherMap;

    public void publishEvent(Event event) {
        transferEventPublisherMap.get(event.getClass()).publish(event);
    }

    void insertPublisher(Class<?> eventClass, EventPublisher eventPublisher) {
        this.transferEventPublisherMap.put(eventClass, eventPublisher);
    }
}
