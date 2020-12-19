package com.tp.webtp.service;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

public class EventService {

    @Autowired
    EventDao eventDao;

    public Event getEventByEventId(UUID idEvent){
        Optional<Event> event = eventDao.findById(idEvent);
        return event.isPresent() ? event.get() : null;
    }
}
