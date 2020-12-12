package com.tp.webtp.entity;

import com.sun.xml.internal.ws.developer.Serialization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue
    private UUID uuid;

    private String email;

    public User( String email) {
        this.email = email;
    }

    public User() {}

    public UUID getId() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
