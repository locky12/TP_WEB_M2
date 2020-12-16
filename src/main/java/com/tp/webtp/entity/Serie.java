package com.tp.webtp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@Entity
@XmlRootElement
public class Serie {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    public Serie(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Serie(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
