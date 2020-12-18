package com.tp.webtp.service;

import com.tp.webtp.dao.TagDao;
import com.tp.webtp.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService {
    @Autowired
    TagDao tagDao;


    public List<Tag> getTagByUserId(UUID idUser) {
        List<Tag> tags = tagDao.getTagNamesByUserId(idUser);
        if(tags != null){
            return tags;
        }
        return null;
    }
}
