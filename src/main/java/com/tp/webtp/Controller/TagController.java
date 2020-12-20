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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    TagService tagService;

    private static String  CACHE_CONTROL_CHAMPS = "Cache-control";
    private static String  CACHE_CONTROL_VALUE = CacheControl.maxAge(Duration.ofDays(1)).cachePrivate().noTransform().mustRevalidate().getHeaderValue();


    @GetMapping
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

    @GetMapping("/{tagName}")
    public ModelAndView getTagEvents(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName) {
        ModelAndView modelAndView;

        if (!StringUtils.hasText(tagName))
            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);

        List<Event> eventList = tagService.getEventsByTagNameAndUserId(tagName, user.getId());

        modelAndView = new ModelAndView("tag");
        modelAndView.addObject("tagName", tagName);
        modelAndView.addObject("events", eventList);
        response.setHeader(CACHE_CONTROL_CHAMPS, CACHE_CONTROL_VALUE);
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

            return ResponseEntity.ok("Entre " + dateDebut.toString() + " et " + dateFin.toString() + ", " + tagName + " a été utilisé " + frequency + " fois !");
        }
        catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
