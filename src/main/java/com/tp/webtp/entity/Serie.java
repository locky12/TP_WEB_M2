package com.tp.webtp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Serie {

    @Id
    private UUID id;
    private String title;
    private String description;

    @OneToMany
    private List<Event> eventList;

    public Serie(String title, String description, List<Event> eventList) {
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
