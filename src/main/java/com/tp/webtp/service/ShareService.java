package com.tp.webtp.service;

import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.entity.Role;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShareService {

    @Autowired
    ShareDao shareDao;

    public Share getShareByShareId(UUID idShare){
        Assert.notNull(idShare, "idShare cannot be null");
        Optional<Share> share = shareDao.findById(idShare);
        return share.orElse(null);
    }

    public List<Share> getSharesByUserId(UUID userId){
        Assert.notNull(userId, "userId cannot be null");
        return shareDao.findByUserId(userId);
    }

    public List<Serie> getSeriesByUserId(UUID userId){
        Assert.notNull(userId, "userId cannot be null");
        return shareDao.getSeriesByUserId(userId);
    }

    public List<Serie> getSeriesByUserIdAndRole(UUID userId, Role role){
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(role, "le role ne peut pas etre null");
        return shareDao.getSeriesByUserIdAndRole(userId, role);
    }

    public List<Serie> getSeriesByUserIdAndNotRole(UUID userId, Role role){
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(role, "le role ne peut pas etre null");
        return shareDao.getSeriesByUserIdAndNotRole(userId, role);
    }

    public Serie getFromUserIdAndSerieId(UUID userId, UUID serieId){
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(serieId, "l'id de la serie ne peut pas etre null");
        Optional<Serie> serie = shareDao.getFromUserIdAndSerieId(userId, serieId);
        return serie.orElse(null);
    }

    public Share getFromUserIdAndSerieIdAndNotRole(UUID userId, UUID serieId, Role role){
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(role, "le role ne peut pas etre null");
        Assert.notNull(serieId, "l'id de la serie ne peut pas etre null");
        Optional<Share> share = shareDao.getFromUserIdAndSerieIdAndNotRole(userId, serieId, role);
        return share.orElse(null);
    }

    public Share getFromUserIdAndSerieIdAndRole(UUID userId, UUID serieId, Role role){
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(role, "le role ne peut pas etre null");
        Assert.notNull(serieId, "l'id de la serie ne peut pas etre null");
        Optional<Share> share = shareDao.getFromUserIdAndSerieIdAndRole(userId, serieId, role);
        return share.orElse(null);
    }

    public Share saveShare(Share share){
        Assert.notNull(share, "share cannot be null");
        return shareDao.save(share);
    }

    public void deleteShare(Share share){
        Assert.notNull(share, "share cannot be null");
        shareDao.delete(share);
    }

    public List<Share> getAllShares(){
        return shareDao.getAll();
    }
}
