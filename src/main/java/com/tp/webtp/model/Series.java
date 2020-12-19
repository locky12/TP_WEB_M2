package com.tp.webtp.model;

import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "series")
@XmlAccessorType(XmlAccessType.FIELD)
public class Series {

    private List<Serie> series;

    public Series(List<Serie> list) {
        this.series = list;
    }

    public Series(){};
    @XmlElement(name="serie")
    public List<Serie> getList() {
        return series;
    }

    public void setList(List<Serie> list) {
        this.series = list;
    }

    @Override
    public String toString() {
        return "Series{" +
                "list=" + series +
                '}';
    }
}
