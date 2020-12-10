package com.tp.webtp.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Event {
    private UUID uuid;
    private String value;
    private Date date;
    private String comment;
    private List<Tag> tags;

    public Event() {
    }

    public void get (){
        date.getTime();
    }
}
