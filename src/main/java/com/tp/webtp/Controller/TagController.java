package com.tp.webtp.Controller;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import com.tp.webtp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.*;

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

    @GetMapping("/")
    public ResponseEntity<List<String>> getTags(HttpServletRequest request, HttpServletResponse response) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID idUser = UUID.fromString(cookie.getValue());

        List<String> tagList = tagDao.getTagNamesByUserId(idUser);

        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/{tagName}")
    public ResponseEntity<List<Event>> getTagEvents(HttpServletRequest request, HttpServletResponse response, @PathVariable("tagName") String tagName) {

        Cookie cookie = WebUtils.getCookie(request, "user");

        if(cookie == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        UUID idUser = UUID.fromString(cookie.getValue());

        if (!StringUtils.hasText(tagName))
            return ResponseEntity.badRequest().build();

        List<Event> eventList = tagDao.getEventsByTagNameAndUserId(tagName, idUser);

        return ResponseEntity.ok(eventList);
    }
}
