package com.tp.webtp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@Entity
@XmlRootElement
public class Serie {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String description;

    public Serie(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Serie(){}

    public UUID getId() {
        return id;
    }
//    @XmlElement
    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
//    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
//    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
