package com.tp.webtp.Controller;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Event;
import com.tp.webtp.model.Tags;
import com.tp.webtp.model.ErrorModel;
import com.tp.webtp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    ShareDao shareDao;
    @Autowired
    SerieDao serieDao;
    @Autowired
    EventDao eventDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    TagService tagService;

    @GetMapping
    public ModelAndView getTags(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView;
        Cookie cookie = WebUtils.getCookie(request, "user");
        if(cookie == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);

        UUID idUser = UUID.fromString(cookie.getValue());

        Tags tags = new Tags(tagService.getTagByUserId(idUser));
        if (tags == null)
            return ErrorModel.createErrorModel(HttpStatus.NOT_FOUND);

        System.out.println("lalalalappppp" + tags);
        modelAndView = new ModelAndView("tags").addObject("tags",tags);
        return modelAndView;
    }

    @GetMapping("/{tagName}")
    public ModelAndView getTagEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName) {
        ModelAndView modelAndView;

        Cookie cookie = WebUtils.getCookie(request, "user");
        if(cookie == null)
            return ErrorModel.createErrorModel(HttpStatus.UNAUTHORIZED);
        UUID idUser = UUID.fromString(cookie.getValue());

        if (!StringUtils.hasText(tagName))
            return ErrorModel.createErrorModel(HttpStatus.BAD_REQUEST);

        List<Event> eventList = tagDao.getEventsByTagNameAndUserId(tagName, idUser);

        modelAndView = new ModelAndView("tag");
        modelAndView.addObject("tagName", tagName);
        modelAndView.addObject("events", eventList);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return modelAndView;
    }

    @GetMapping("/{tagName}/lastdate")
    public ResponseEntity<String> getLastTagEvent(HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID idUser = UUID.fromString(cookie.getValue());

        if (!StringUtils.hasText(tagName))
            return ResponseEntity.badRequest().build();

        Date lastDate = tagDao.getEventsDateByTagNameAndUserId(tagName, idUser);

        cookie.setMaxAge(5000);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(lastDate.toString());
    }

    @GetMapping("/{tagName}/frequency")
    public ResponseEntity<String> getTagFrequency(HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName, @RequestParam String debut, @RequestParam String fin) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID idUser = UUID.fromString(cookie.getValue());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dateDebut = formatter.parse(debut);
            Date dateFin = formatter.parse(fin);

            Integer frequency = tagDao.getTagFrequencyBetweenDates(tagName, idUser, dateDebut, dateFin);

            cookie.setMaxAge(5000);
            cookie.setPath("/");
            response.addCookie(cookie);
            return ResponseEntity.ok("Entre " + dateDebut.toString() + " et " + dateFin.toString() + ", " + tagName + " a été utilisé " + frequency + " fois !");
        }
        catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
