package com.tp.webtp.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class Series {

    @Id
    private Long id;

    @OneToMany
    private List<Serie> seriesList;

}
