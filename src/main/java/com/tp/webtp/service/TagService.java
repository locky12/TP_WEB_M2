package com.tp.webtp.service;

import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        return tag.isPresent() ? tag.get() : null;
    }

    public List<Tag> getTagsByUserId(UUID idUser) {
        List<Tag> tags = tagDao.getTagsByUserId(idUser);
        return tags.isEmpty() ? null : tags;
    }

    public List<Event> getEventsByTagNameAndUserId(String tagName, UUID userId){
        if(tagName == null || !StringUtils.hasText(tagName)) return null;
        List<Event> events = tagDao.getEventsByTagNameAndUserId(tagName, userId);
        return events.isEmpty() ? null : events;
    }

    public List<Tag> getTagsByUserIdAndEventId(UUID eventId, UUID userId){
        List<Tag> tags = tagDao.getTagsByUserIdAndEventId(eventId, userId);
        return tags.isEmpty() ? null : tags;
    }

    public Date getEventsDateByTagNameAndUserId(String tagName, UUID userId){
        if(tagName == null || !StringUtils.hasText(tagName)) return null;
        Date date = tagDao.getEventsDateByTagNameAndUserId(tagName, userId);
        return date;
    }

    public Integer getTagFrequencyBetweenDates(String tagName, UUID userId, Date debut, Date fin){
        if(tagName == null || !StringUtils.hasText(tagName) || debut == null || fin == null || debut.after(fin)) return null;
        Integer integer = tagDao.getTagFrequencyBetweenDates(tagName, userId, debut, fin);
        return integer;
    }

}
