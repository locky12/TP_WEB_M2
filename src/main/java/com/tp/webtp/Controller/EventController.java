package com.tp.webtp.Controller;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.*;
import com.tp.webtp.model.ErrorModel;
import com.tp.webtp.service.EventService;
import com.tp.webtp.service.SerieService;
import com.tp.webtp.service.ShareService;
import com.tp.webtp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/series/")
public class EventController {

    @Autowired
    ShareService shareService;
    @Autowired
    SerieService serieService;
    @Autowired
    EventService eventService;
    @Autowired
    TagService tagService;
    @Autowired
    EventDao eventDao;
    @Autowired
    TagDao tagDao;

    @GetMapping("{idSerie}/events")
    public ModelAndView getEvents( @PathVariable("idSerie")  UUID idSerie, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView;

        Cookie cookie = WebUtils.getCookie(request, "user");
        if(cookie == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);
        UUID idUser = UUID.fromString(cookie.getValue());

        Serie serie = shareService.getFromUserIdAndSerieId(idUser, idSerie);

        if (serie == null)
            return ErrorModel.createErrorModel(HttpStatus.NOT_FOUND);

        List<Event> eventList = eventService.getBySerieId(idSerie);
        for (Event event : eventList){
            UUID idEvent = event.getId();
            Link thisLink = linkTo(methodOn(this.getClass()).getEvents(idSerie,request,response)).slash(event.getId()).withSelfRel();
            Link tagLink = linkTo(methodOn(this.getClass()).getEvents(idSerie,request,response)).slash(event.getId()).slash("tags").withRel("tag");
            event.add(thisLink);
            event.add(tagLink);
        }

        modelAndView = new ModelAndView("events");
        modelAndView.addObject("events", eventList);
        modelAndView.addObject("serie", serie);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return modelAndView;
    }

    @PostMapping("{idSerie}/events")
    public ResponseEntity<Event> createEvent(HttpServletResponse response, HttpServletRequest request, @PathVariable("idSerie") UUID idSerie, @RequestBody Event eventR) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID idUser = UUID.fromString(cookie.getValue());

        Serie serie = serieService.getSerieBySerieId(idSerie);

        if(eventR == null)
            return ResponseEntity.badRequest().build();

        if(shareService.getFromUserIdAndSerieIdAndNotRole(idUser, idSerie, Role.READ) == null)
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        eventR.setSerie(serie);
        eventR.setDateModif(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        Event event = eventDao.save(eventR);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.created(URI.create("/series/" + serie.getId() + "/" + event.getId())).build();
    }

    @GetMapping("{idSerie}/events/{idEvent}")
    public ModelAndView getEvent(@PathVariable("idSerie")  UUID idSerie, @PathVariable("idEvent")  UUID idEvent, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView;
        Cookie cookie = WebUtils.getCookie(request, "user");
        if(cookie == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);

        UUID idUser = UUID.fromString(cookie.getValue());

        Event event = eventService.getEventByEventId(idEvent);
        if(event == null || !event.getSerie().getId().equals(idSerie))
            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);

        if(shareService.getFromUserIdAndSerieId(idUser, idSerie) == null)
            return ErrorModel.createErrorModel(HttpStatus.METHOD_NOT_ALLOWED);

        List<Tag> tagList = tagService.getTagsByUserIdAndEventId(idEvent, idUser);
        Link thisLink = linkTo(methodOn(this.getClass()).getEvents(idSerie,request,response)).slash(event.getId()).withSelfRel();
        Link tagLink = linkTo(methodOn(this.getClass()).getEvents(idSerie,request,response)).slash(event.getId()).slash("tags").withRel("tag");
        event.add(thisLink);
        event.add(tagLink);

        modelAndView = new ModelAndView("event");
        modelAndView.addObject("event", event);
        modelAndView.addObject("tags", tagList);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return modelAndView;
    }

    @PutMapping("{idSerie}/events/{idEvent}")
    public ResponseEntity<Event> updateEvent(@PathVariable("idSerie")  UUID idSerie, @PathVariable("idEvent")  UUID idEvent, @RequestBody Event eventR, HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        
        UUID idUser = UUID.fromString(cookie.getValue());

        Event event = eventService.getEventByEventId(idEvent);
        if(event == null || !event.getSerie().getId().equals(idSerie))
            return ResponseEntity.badRequest().build();

        if(shareService.getFromUserIdAndSerieId(idUser, idSerie) == null)
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        eventR.setId(idEvent);
        eventR.setSerie(event.getSerie());
        eventR.setDateModif(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        eventDao.save(eventR);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(event);
    }

    @GetMapping("{idSerie}/events/{idEvent}/tags")
    public ModelAndView getEventTags(@PathVariable("idSerie")  UUID idSerie, @PathVariable("idEvent")  UUID idEvent, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView;

        Cookie cookie = WebUtils.getCookie(request, "user");
        if(cookie == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);
        UUID idUser = UUID.fromString(cookie.getValue());

        Event event = eventService.getEventByEventId(idEvent);
        if(event == null || !event.getSerie().getId().equals(idSerie))
            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);

        if(shareService.getFromUserIdAndSerieId(idUser, idSerie) == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);

        List<Tag> tagList = tagService.getTagsByUserIdAndEventId(idEvent, idUser);

        modelAndView = new ModelAndView("tags");
        modelAndView.addObject("tags", tagList);

        Link thisLink = linkTo(methodOn(this.getClass()).getEventTags(idSerie,idEvent,request,response)).withSelfRel();
        event.add(thisLink);
        for(Tag tag : tagList){
            Link link = linkTo(methodOn(TagController.class).getTagEvents(request,response,tag.getTagName())).withRel("Alltags");
            event.add(link);
        }

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return modelAndView;
    }

    @PostMapping("{idSerie}/events/{idEvent}/tags")
    public ResponseEntity<Event> createEventTag(HttpServletResponse response, HttpServletRequest request, @PathVariable("idSerie")  UUID idSerie, @PathVariable("idEvent")  UUID idEvent, @RequestBody Tag tagR) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID idUser = UUID.fromString(cookie.getValue());

        Event event = eventService.getEventByEventId(idEvent);
        if(event == null || !event.getSerie().getId().equals(idSerie))
            return ResponseEntity.badRequest().build();

        if(shareService.getFromUserIdAndSerieIdAndNotRole(idUser, event.getSerie().getId(), Role.READ) == null)
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();

        tagR.setEvent(event);
        tagDao.save(tagR);

        event.setDateModif(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        eventDao.save(event);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.created(URI.create("/series/" + event.getSerie().getId() + "/" + event.getId() + "/tags")).build();
    }
}
