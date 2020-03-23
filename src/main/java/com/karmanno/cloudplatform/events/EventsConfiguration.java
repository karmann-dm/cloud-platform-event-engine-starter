package com.karmanno.cloudplatform.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

@Configuration
@ConditionalOnProperty(value = "events.enabled", havingValue = "true")
public class EventsConfiguration {
    public static final String PUBLISHER_BEAN_FACTORY_NAME = "publisherFactory";

    @Bean
    @ConfigurationProperties(prefix = "events")
    public EventsProperties eventsProperties() {
        return new EventsProperties();
    }

    @ConditionalOnMissingBean(ProducerFactory.class)
    public ProducerFactory<String, String> producerFactory(EventsProperties eventsProperties) {
        HashMap<String, Object> props = new HashMap<String, Object>() {{
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, eventsProperties.getKafkaServer());
        }};
        return new DefaultKafkaProducerFactory<>(props);
    }

    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public EventsBeanDefinitionRegistryPostProcessor eventsBeanDefinitionFactoryPostProcessor(Environment environment) {
        return new EventsBeanDefinitionRegistryPostProcessor(environment);
    }

    @Bean
    public EventPublisherBeanPostProcessor eventPublisherBeanPostProcessor(ApplicationContext applicationContext) {
        EventPublisherBeanPostProcessor eventPublisherBeanPostProcessor = new EventPublisherBeanPostProcessor();
        eventPublisherBeanPostProcessor.setApplicationContext(applicationContext);
        return eventPublisherBeanPostProcessor;
    }

    @Bean(name = PUBLISHER_BEAN_FACTORY_NAME)
    public EventPublisherBeanFactory publisherFactory(KafkaTemplate<String, String> kafkaTemplate,
                                                      ObjectMapper objectMapper) {
        return new EventPublisherBeanFactory(kafkaTemplate, objectMapper);
    }

}
