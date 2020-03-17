package com.karmanno.cloudplatform.events;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.Map;

public class EventsBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        EventPublisherContainer container = beanFactory.getBean(EventPublisherContainer.class);
        Map<String, EventPublisher> configuredPublishers = beanFactory.getBeansOfType(EventPublisher.class);
        container.setEventPublishers(new ArrayList<>(configuredPublishers.values()));
    }
}
