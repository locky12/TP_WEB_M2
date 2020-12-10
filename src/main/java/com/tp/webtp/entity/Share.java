package com.tp.webtp.entity;

public class Share {
    private Long idSerie;
    private Long idUser;
    private Boolean write;


    public Share(Long idSerie, Long idUser, Boolean write) {
        this.idSerie = idSerie;
        this.idUser = idUser;
        this.write = write;
    }

    public Share() {}

    public Long getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Long idSerie) {
        this.idSerie = idSerie;
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
