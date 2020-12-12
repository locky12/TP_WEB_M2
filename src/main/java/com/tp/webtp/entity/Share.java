package com.tp.webtp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Share {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID uuidSerie;
    private UUID uuidUser;
    private Boolean write;

    public Share(UUID uuidSerie, UUID uuidUser, Boolean write) {
        this.uuidSerie = uuidSerie;
        this.uuidUser = uuidUser;
        this.write = write;
    }

    public Share() {}

    public UUID getUuidSerie() {
        return uuidSerie;
    }

    public void setUuidSerie(UUID uuidSerie) {
        this.uuidSerie = uuidSerie;
    }

    public UUID getIdUser() {
        return uuidUser;
    }

    public void setIdUser(UUID idUser) {
        this.uuidUser = idUser;
    }

    public Boolean getWrite() {
        return write;
    }

    public void setWrite(Boolean write) {
        this.write = write;
    }
}
