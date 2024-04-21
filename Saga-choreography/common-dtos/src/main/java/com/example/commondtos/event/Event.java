package com.example.commondtos.event;

import java.util.Date;
import java.util.UUID;

public interface Event {
    UUID getEventID();

    Date getDate();

}
