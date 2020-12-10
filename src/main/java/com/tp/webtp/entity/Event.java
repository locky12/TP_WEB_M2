package com.tp.webtp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Event {

    @Id
    private UUID uuid;
    private String value;
    private Date date;
    private String comment;

    @OneToMany
    private List<Tag> tags;

    public Event() {
    }

    public void get (){
        date.getTime();
    }
}
