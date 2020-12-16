package com.tp.webtp.entity;

import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Entity
public class Event {


    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "serie")
    private Serie serie;

    private String value;
    private Date date;
    private String comment;

    private Date dateModif;

    public Event(String value, String comment) {
        this.date = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        this.value = value;
        this.comment = comment;
    }

    public Event(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void get(){
        date.getTime();
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Date getDateModif() {
        return dateModif;
    }

    public void setDateModif(Date dateModif) {
        this.dateModif = dateModif;
    }
}
