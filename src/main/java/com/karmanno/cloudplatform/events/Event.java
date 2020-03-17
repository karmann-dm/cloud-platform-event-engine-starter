package com.karmanno.cloudplatform.events;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public interface Event{
    String getId();
    String getEmitter();
    LocalDateTime getTimestamp();

    Supplier<String> getRepresentation();
}
