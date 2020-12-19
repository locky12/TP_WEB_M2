package com.tp.webtp.service;

import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.entity.Role;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShareService {

    @Autowired
    ShareDao shareDao;

    public Share getShareByShareId(UUID idShare){
        Optional<Share> share = shareDao.findById(idShare);
        return share.orElse(null);
    }

    public List<Share> getSharesByUserId(UUID userId){
        List<Share> shares = shareDao.findByUserId(userId);
        return shares.isEmpty() ? null : shares;
    }

    public List<Serie> getSeriesByUserId(UUID userId){
        List<Serie> series = shareDao.getSeriesByUserId(userId);
        return series.isEmpty() ? null : series;
    }

    public List<Serie> getSeriesByUserIdAndRole(UUID userId, Role role){
        if(role == null) return null;
        List<Serie> series = shareDao.getSeriesByUserIdAndRole(userId, role);
        return series.isEmpty() ? null : series;
    }

    public List<Serie> getSeriesByUserIdAndNotRole(UUID userId, Role role){
        if(role == null) return null;
        List<Serie> series = shareDao.getSeriesByUserIdAndNotRole(userId, role);
        return series.isEmpty() ? null : series;
    }

    public Serie getFromUserIdAndSerieId(UUID userId, UUID serieId){
        if(serieId == null) return null;
        Optional<Serie> serie = shareDao.getFromUserIdAndSerieId(userId, serieId);
        return serie.orElse(null);
    }

    public Share getFromUserIdAndSerieIdAndNotRole(UUID userId, UUID serieId, Role role){
        if(serieId == null || role == null) return null;
        Optional<Share> share = shareDao.getFromUserIdAndSerieIdAndNotRole(userId, serieId, role);
        return share.orElse(null);
    }

    public Share getFromUserIdAndSerieIdAndRole(UUID userId, UUID serieId, Role role){
        if(serieId == null || role == null) return null;
        Optional<Share> share = shareDao.getFromUserIdAndSerieIdAndRole(userId, serieId, role);
        return share.orElse(null);
    }
}
