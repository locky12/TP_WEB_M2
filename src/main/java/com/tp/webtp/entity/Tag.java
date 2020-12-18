package com.tp.webtp.entity;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Entity
@XmlRootElement
public class Tag {

    @Id
    @GeneratedValue
    private UUID id;

    private String tagName;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Tag(String tagName, Event event) {
        Assert.hasText(tagName, "tag cannot be null,empty or blank");
        this.tagName = tagName;
        this.event = event;
    }

    public Tag(String tagName) {
        Assert.hasText(tagName, "tag cannot be null,empty or blank");
        this.tagName = tagName;
    }

    public Tag() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
