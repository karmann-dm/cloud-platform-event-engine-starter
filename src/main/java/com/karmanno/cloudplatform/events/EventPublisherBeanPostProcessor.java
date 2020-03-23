package com.karmanno.cloudplatform.events;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EventPublisherBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EventPublisher) {
            EventPublisher eventPublisher = (EventPublisher) bean;

            EventPublisherContainer container = applicationContext.getBean(EventPublisherContainer.class);
            container.addPublisher(eventPublisher);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
