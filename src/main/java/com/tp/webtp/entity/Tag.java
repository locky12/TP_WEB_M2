package com.tp.webtp.entity;

import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "TAG")
public class Tag {
    @Id
    @GeneratedValue
    @Column(name ="Id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String tagName;

    public Tag(String tagName) {
        Assert.hasText(tagName, "tag cannot be null,empty or blank");

        this.tagName = tagName;
    }


    public Long getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
