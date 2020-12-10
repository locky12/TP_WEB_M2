package com.tp.webtp.entity;

import java.util.List;
import java.util.UUID;

public class Serie {
    private UUID id;
    private String title;
    private String description;
    private EventList eventList;

    public Serie(String title, String description, EventList eventList) {
        this.title = title;
        this.description = description;
        eventList = eventList;
    }

    public Serie(){}

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
