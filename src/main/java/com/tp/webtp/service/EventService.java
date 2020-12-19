package com.tp.webtp.service;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    EventDao eventDao;

    public Event getEventByEventId(UUID idEvent){
        Optional<Event> event = eventDao.findById(idEvent);
        return event.orElse(null);
    }

    public List<Event> getBySerieId(UUID idSerie){
        return eventDao.findBySerieId(idSerie);
    }
}
