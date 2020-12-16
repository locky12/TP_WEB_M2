package com.tp.webtp.Controller;


import com.tp.webtp.dao.EventDao;
import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/series/")
public class EventController {

    @Autowired
    ShareDao shareDao;
    @Autowired
    SerieDao serieDao;
    @Autowired
    EventDao eventDao;
    @Autowired
    TagDao tagDao;

    @GetMapping("{idSerie}/events/")
    public ResponseEntity<List<Event>> getEvents( @PathVariable("idSerie")  UUID idSerie, HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Serie> serie = shareDao.getFromUserIdAndSerieId(idUser, idSerie);

        if (!serie.isPresent())
            return ResponseEntity.notFound().build();

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(eventDao.findBySerieId(idSerie));
    }

    @PostMapping("{idSerie}/events")
    public ResponseEntity<Event> createEvent(HttpServletResponse response, HttpServletRequest request, @PathVariable("idSerie") UUID idSerie, @RequestBody Event eventR) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Serie> serie = serieDao.findById(idSerie);

        if(eventR == null || !serie.isPresent())
            return ResponseEntity.badRequest().build();

        if(!shareDao.getFromUserIdAndSerieIdAndNotRole(idUser, idSerie, Role.READ).isPresent())
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        eventR.setSerie(serie.get());
        Event event = eventDao.save(eventR);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.created(URI.create("/series/" + serie.get().getId() + "/" + event.getId())).build();
    }

    @GetMapping("{idSerie}/events/{idEvent}/")
    public ResponseEntity<Event> getEvent(@PathVariable("idSerie")  UUID idSerie, @PathVariable("idEvent")  UUID idEvent, HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Event> event = eventDao.findById(idEvent);
        if(!event.isPresent() || !event.get().getSerie().getId().equals(idSerie))
            return ResponseEntity.badRequest().build();

        if(!shareDao.getFromUserIdAndSerieId(idUser, idSerie).isPresent())
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(event.get());
    }

    @GetMapping("{idSerie}/events/{idEvent}/tags")
    public ResponseEntity<List<Tag>> getEventTags(@PathVariable("idSerie")  UUID idSerie, @PathVariable("idEvent")  UUID idEvent, HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Event> event = eventDao.findById(idEvent);
        if(!event.isPresent() || !event.get().getSerie().getId().equals(idSerie))
            return ResponseEntity.badRequest().build();

        if(!shareDao.getFromUserIdAndSerieId(idUser, idSerie).isPresent())
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        List<Tag> tagList = tagDao.getTagsByUserIdAndEventId(idEvent, idUser);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(tagList);
    }

    @PostMapping("{idSerie}/events/{idEvent}/tags")
    public ResponseEntity<Event> createEventTag(HttpServletResponse response, HttpServletRequest request, @PathVariable("idSerie")  UUID idSerie, @PathVariable("idEvent")  UUID idEvent, @RequestBody Tag tagR) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null){
            //TODO redirect
            return ResponseEntity.status(418).build();
        }

        UUID idUser = UUID.fromString(cookie.getValue());

        Optional<Event> event = eventDao.findById(idEvent);
        if(!event.isPresent() || !event.get().getSerie().getId().equals(idSerie))
            return ResponseEntity.badRequest().build();

        if(!shareDao.getFromUserIdAndSerieIdAndNotRole(idUser, event.get().getSerie().getId(), Role.READ).isPresent())
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        tagR.setEvent(event.get());
        tagDao.save(tagR);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.created(URI.create("/series/" + event.get().getSerie().getId() + "/" + event.get().getId() + "/tags")).build();
    }

    @GetMapping("e/t")
    public Event test(){
        return new Event("12","un commentaire");
    }
}
