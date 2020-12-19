package com.tp.webtp.model;

import com.tp.webtp.entity.Tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tags")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tags {

    private List<Tag> tags;

    public Tags(List<Tag> list) {
        this.tags = list;
    }

    public Tags(){};
    @XmlElement(name="tag")
    public List<Tag> getList() {
        return tags;
    }

    public void setList(List<Tag> list) {
        this.tags = list;
    }

    @Override
    public String toString() {
        return "Tags{" +
                 ""+ tags +","+
                '}';
    }
}
