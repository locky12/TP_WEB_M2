package com.tp.webtp.entity;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Tag {

    @Id
    @GeneratedValue
    private UUID uuid;
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Tag(String tagName) {
        Assert.hasText(tagName, "tag cannot be null,empty or blank");
        this.tagName = tagName;
    }

    public Tag() {}

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
