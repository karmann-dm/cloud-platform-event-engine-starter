package com.karmanno.cloudplatform.events;

import lombok.Data;

@Data
public class EventTransfer {
    Class<?> transferClass;
    String topicName;
}
