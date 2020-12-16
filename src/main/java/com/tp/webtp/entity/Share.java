package com.tp.webtp.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Share {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    private Role role;

    public Share() {}

    public Share(User user, Serie serie, Role role) {
        this.user = user;
        this.serie = serie;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

