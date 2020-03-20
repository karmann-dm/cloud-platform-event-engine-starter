package com.karmanno.cloudplatform.events;

public class EventPublisherContainerBeanFactory {
    public Object create() {
        return new EventPublisherContainer();
    }
}
