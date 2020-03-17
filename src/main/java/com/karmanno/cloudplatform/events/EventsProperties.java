package com.karmanno.cloudplatform.events;

import java.util.List;
import java.util.Map;

public class EventsProperties {

    public static class ListenEntry {
        private Class<?> event;
        private Class<?> listener;

        public Class<?> getEvent() {
            return event;
        }

        public void setEvent(Class<?> event) {
            this.event = event;
        }

        public Class<?> getListener() {
            return listener;
        }

        public void setListener(Class<?> listener) {
            this.listener = listener;
        }
    }

    private Boolean enabled;
    private Map<String, Class<?>> publish;
    private Map<String, List<ListenEntry>> listen;


    public Map<String, Class<?>> getPublish() {
        return publish;
    }

    public void setPublish(Map<String, Class<?>> publish) {
        this.publish = publish;
    }

    public Map<String, List<ListenEntry>> getListen() {
        return listen;
    }

    public void setListen(Map<String, List<ListenEntry>> listen) {
        this.listen = listen;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
