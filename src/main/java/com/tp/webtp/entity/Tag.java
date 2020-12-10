package com.tp.webtp.entity;

import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String tagName;

    public Tag(String tagName) {
        Assert.hasText(tagName, "tag cannot be null,empty or blank");

        this.tagName = tagName;
    }

    public Tag() {}

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
