package com.karmanno.cloudplatform.events;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "events")
@Data
public class EventsProperties {
    private String kafkaBootstrapServers;
    private Map<String, Class<?>> topics;
}
