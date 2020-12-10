package com.tp.webtp;

import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class Controller {
    @Autowired
    TagDao tagDao;

    @GetMapping("/test")
    public String get () {
        Tag tag = new Tag("tag1");
        Tag tag1 = new Tag("tag2");
        tagDao.save(tag);
        tagDao.save(tag1);
        tagDao.delete(tag);


        return tagDao.findAll().toString();
    }
}
