package com.tp.webtp.Controller;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Tag;
import com.tp.webtp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


@RestController
public class ControllerTest {

    @Autowired
    EventDao eventDAO;

    @GetMapping("/test")
    public String get () {
        Event tag = new Event();
        Event tag1 = new Event();
        eventDAO.save(tag);
        eventDAO.save(tag1);
        eventDAO.delete(tag);

        Iterator<Event> i = eventDAO.findAll().iterator();
        if(i.hasNext()){
            return i.next().getId().toString();
        }

        return eventDAO.findAll().toString();
    }
    @GetMapping(path = "/test1")
    public User getTest(){
        User user = new User();
        return user;
    }
}
