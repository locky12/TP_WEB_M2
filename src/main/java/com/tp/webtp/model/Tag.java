package com.tp.webtp.model;

import org.springframework.util.Assert;

public class Tag {
    private String id;
    private String tagName;

    public Tag(String tagName) {
        Assert.hasText(tagName, "tag cannot be null,empty or blank");

        this.tagName = tagName;
    }


    public String getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
