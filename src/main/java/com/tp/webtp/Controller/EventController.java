package com.tp.webtp.Controller;


import com.tp.webtp.dao.EventDao;
import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/series/")
public class EventController {

    @Autowired
    SerieDao serieDao;

    @Autowired
    EventDao eventDao;

    @PostMapping("{id}/events")
    public ResponseEntity<Serie> createEvent(HttpServletResponse response, HttpServletRequest request, @PathVariable("id") UUID id, @RequestBody Event eventR) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        Optional<Serie> serie;
        serie =  serieDao.findById(id);

        if (!serie.isPresent() )
            return ResponseEntity.notFound().build();

        if(eventR == null)
            return ResponseEntity.badRequest().build();

        Event event;
        System.out.println(eventR.getComment());
        eventR.setSerie(serie.get());
        event = eventDao.save(eventR);

        return  ResponseEntity.created(URI.create("/series/" + serie.get().getId() + "/" + event.getId())).build();
    }

    @GetMapping("{id}/events/")
    public ResponseEntity<List<Event>> getEvent( @PathVariable("id")  UUID id, HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Serie> serie;
        serie =  serieDao.findById(id);
        List<Event> e = eventDao.findAll();
        for (Event i : e){
            System.out.println(i.getComment());
        }
        List<Event> events = eventDao.findBySerieId(id);
        System.out.println(events.toString());
        if (!serie.isPresent())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(events);

    }

    @GetMapping("e/t")
    public Event readCookie(){
        Event serie = new Event("12","un commentaire");

        return serie;
    }






}
