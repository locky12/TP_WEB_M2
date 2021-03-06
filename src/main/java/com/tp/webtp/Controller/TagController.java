package com.tp.webtp.Controller;

import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Tag;
import com.tp.webtp.entity.User;
import com.tp.webtp.model.ErrorModel;
import com.tp.webtp.model.Tags;
import com.tp.webtp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    private static final String  CACHE_CONTROL_CHAMPS = "Cache-control";
    private static final String  CACHE_CONTROL_VALUE = CacheControl.maxAge(Duration.ofDays(1)).cachePrivate().noTransform().mustRevalidate().getHeaderValue();
    private static final String  LAST_MODIFIED_CHAMPS = "Last-Modified";
    private static final SimpleDateFormat LAST_MODIFIED_FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ModelAndView getTags(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView;

        Tags tags = new Tags(tagService.getTagsByUserId(user.getId()));
        if (tags.getList().isEmpty())
            return ErrorModel.createErrorModel(HttpStatus.NOT_FOUND);

        for (Tag tag : tags.getList()) {
            Link link = linkTo(methodOn(TagController.class).getTagEvents(user, request,response,tag.getTagName())).withSelfRel();
            tag.add(link);
            Link linklastDate= linkTo(methodOn(TagController.class).getLastTagEvent(user, request,response,tag.getTagName())).withRel("lastDate tag");
            tag.add(linklastDate);
        }

        modelAndView = new ModelAndView("tags").addObject("tags",tags);
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
        return modelAndView;
    }

    @GetMapping(value = "/{tagName}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE, MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ModelAndView getTagEvents(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName) {
        ModelAndView modelAndView;

        if (!StringUtils.hasText(tagName))
            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);

        List<Event> eventList = tagService.getEventsByTagNameAndUserId(tagName, user.getId());

        Date lastDate = eventList.stream().map(Event::getDateModif).max(Date::compareTo).get();

        modelAndView = new ModelAndView("tag");
        modelAndView.addObject("tagName", tagName);
        modelAndView.addObject("events", eventList);
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
        LAST_MODIFIED_FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
        response.setHeader(LAST_MODIFIED_CHAMPS, LAST_MODIFIED_FORMATTER.format(lastDate));
        return modelAndView;
    }

    @GetMapping("/{tagName}/lastdate")
    public ResponseEntity<String> getLastTagEvent(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName) {

        if (!StringUtils.hasText(tagName))
            return ResponseEntity.badRequest().build();

        Date lastDate = tagService.getEventsDateByTagNameAndUserId(tagName, user.getId());
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
        return ResponseEntity.ok(lastDate.toString());
    }

    @GetMapping("/{tagName}/frequency")
    public ResponseEntity<String> getTagFrequency(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName, @RequestParam String debut, @RequestParam String fin) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dateDebut = formatter.parse(debut);
            Date dateFin = formatter.parse(fin);

            Integer frequency = tagService.getTagFrequencyBetweenDates(tagName, user.getId(), dateDebut, dateFin);

            List<Event> eventList = tagService.getEventsByTagNameAndUserId(tagName, user.getId());
            Date lastDate = eventList.stream().map(Event::getDateModif).max(Date::compareTo).get();

            response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
            LAST_MODIFIED_FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
            response.setHeader(LAST_MODIFIED_CHAMPS, LAST_MODIFIED_FORMATTER.format(lastDate));

            return ResponseEntity.ok("Entre " + dateDebut.toString() + " et " + dateFin.toString() + ", " + tagName + " a été utilisé " + frequency + " fois !");
        }
        catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
