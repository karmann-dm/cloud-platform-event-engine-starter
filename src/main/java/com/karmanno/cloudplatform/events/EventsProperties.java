package com.karmanno.cloudplatform.events;

import java.util.List;
import java.util.Map;

public class EventsProperties {

    private Boolean enabled;
    private Map<String, Class<?>> publish;
    private String kafkaServer;

    public Map<String, Class<?>> getPublish() {
        return publish;
    }

    public void setPublish(Map<String, Class<?>> publish) {
        this.publish = publish;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getKafkaServer() {
        return kafkaServer;
    }

    public void setKafkaServer(String kafkaServer) {
        this.kafkaServer = kafkaServer;
    }
}
