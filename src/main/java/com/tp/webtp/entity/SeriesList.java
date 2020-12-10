package com.tp.webtp.entity;

import java.util.List;

public class SeriesList {
    private List<Serie> list;

    public void addSerie (Serie serie) {
        list.add(serie);
    }

    public void removeSerie(Serie serie) {
        list.remove(serie);
    }

    public  Serie getSerie(int index) {
        return list.get(index);
    }
}
