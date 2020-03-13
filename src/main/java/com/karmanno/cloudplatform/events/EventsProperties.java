package com.karmanno.cloudplatform.events;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "events")
public class EventsProperties {
    private String kafkaBootstrapServers;
    private Map<String, Class<?>> topics;

    public String getKafkaBootstrapServers() {
        return kafkaBootstrapServers;
    }

    public void setKafkaBootstrapServers(String kafkaBootstrapServers) {
        this.kafkaBootstrapServers = kafkaBootstrapServers;
    }

    public Map<String, Class<?>> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, Class<?>> topics) {
        this.topics = topics;
    }
}
