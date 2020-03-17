package com.karmanno.cloudplatform.events;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

public class EventsBeanDefinitionFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final String PUBLISHER_FACTORY_NAME = "publisherFactory";
    private static final String FACTORY_METHOD_NAME = "create";

    private EventsProperties eventsProperties;

    public EventsBeanDefinitionFactoryPostProcessor(Environment environment) {
        eventsProperties = Binder.get(environment)
                .bind("events", Bindable.of(EventsProperties.class))
                .orElseThrow(() -> new RuntimeException("Can't bind events properties to class " +
                        EventsProperties.class.getCanonicalName())
                );
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        List<String> publisherNames = new ArrayList<>();

        eventsProperties.getPublish().forEach((topic, eventClass) -> {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(EventPublisher.class);
            beanDefinition.setFactoryBeanName(PUBLISHER_FACTORY_NAME);
            beanDefinition.setFactoryMethodName(FACTORY_METHOD_NAME);

            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, eventClass);
            constructorArgumentValues.addIndexedArgumentValue(1, topic);

            String beanName = topic + "_bean";
            publisherNames.add(beanName);

            registry.registerBeanDefinition(beanName, beanDefinition);
        });

        GenericBeanDefinition containerBeanDefinition = new GenericBeanDefinition();
        containerBeanDefinition.setBeanClass(EventPublisherContainer.class);
        containerBeanDefinition.setDependsOn((String[]) publisherNames.toArray());

        registry.registerBeanDefinition("eventPusherContainer", containerBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}
