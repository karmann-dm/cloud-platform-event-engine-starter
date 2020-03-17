package com.karmanno.cloudplatform.events;

public interface EventPublisher {
    Class<?> getAcceptableTransferClass();
    void publish(Event event);
}
