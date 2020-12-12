package com.tp.webtp.service;

import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.entity.Share;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class ShareService {

    @Autowired
    ShareDao shareDao;
    /*
    public List<UUID> getAllUUID(UUID userId) throws IllegalAccessException {

        if(userId == null)
            throw new IllegalAccessException();

        List<Share> shareList = shareDao.findAll();
    }*/
}
