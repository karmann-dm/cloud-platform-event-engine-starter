package com.karmanno.cloudplatform.events;

import java.time.LocalDateTime;

public interface Event{
    String getId();
    LocalDateTime getTimestamp();
    Object getPayload();
}
