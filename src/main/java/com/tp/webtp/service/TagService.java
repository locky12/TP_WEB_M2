package com.tp.webtp.service;

import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagService {

    @Autowired
    TagDao tagDao;

    public Tag getTagByTagId(UUID idTag){
        Optional<Tag> tag = tagDao.findById(idTag);
        return tag.orElse(null);
    }

    public List<Tag> getTagsByUserId(UUID idUser) {
        return tagDao.getTagsByUserId(idUser);
    }

    public List<Event> getEventsByTagNameAndUserId(String tagName, UUID userId){
        Assert.hasText(tagName, "tagName ne peut pas etre empty/blank/null");
        return tagDao.getEventsByTagNameAndUserId(tagName, userId);
    }

    public List<Tag> getTagsByUserIdAndEventId(UUID eventId, UUID userId){
        return tagDao.getTagsByUserIdAndEventId(eventId, userId);
    }

    public Date getEventsDateByTagNameAndUserId(String tagName, UUID userId){
        Assert.hasText(tagName, "tagName ne peut pas etre empty/blank/null");
        Date date = tagDao.getEventsDateByTagNameAndUserId(tagName, userId);
        return date;
    }

    public Integer getTagFrequencyBetweenDates(String tagName, UUID userId, Date debut, Date fin){
        Assert.hasText(tagName, "tagName ne peut pas etre empty/blank/null");
        Assert.notNull(debut, "la date de debut ne peut pas etre null");
        Assert.notNull(fin, "la date de fin ne peut pas etre null");
        Assert.isTrue(debut.before(fin), "la date de fin ne peut pas etre plus recente que la date de debut");
        Integer integer = tagDao.getTagFrequencyBetweenDates(tagName, userId, debut, fin);
        return integer;
    }

}
