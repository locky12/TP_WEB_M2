package com.tp.webtp.model;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID uuid;
    private String value;
    private Calendar calendar;

    public void get (){
        calendar.getTime();
    }
}
