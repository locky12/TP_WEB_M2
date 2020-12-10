package com.tp.webtp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Share {

    @Id
    private UUID uuidSerie;
    private Long idUser;
    private Boolean write;

    public Share(UUID uuidSerie, Long idUser, Boolean write) {
        this.uuidSerie = uuidSerie;
        this.idUser = idUser;
        this.write = write;
    }

    public Share() {}

    public UUID getUuidSerie() {
        return uuidSerie;
    }

    public void setUuidSerie(UUID uuidSerie) {
        this.uuidSerie = uuidSerie;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Boolean getWrite() {
        return write;
    }

    public void setWrite(Boolean write) {
        this.write = write;
    }
}
