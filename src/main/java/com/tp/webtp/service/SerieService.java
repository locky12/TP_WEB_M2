package com.tp.webtp.service;

import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.entity.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

@Service
public class SerieService {

    @Autowired
    SerieDao serieDao;

    public Serie getSerieBySerieId(UUID idSerie){
        Assert.notNull(idSerie, "idSerie cannot be null");
        Optional<Serie> serie = serieDao.findById(idSerie);
        return serie.orElse(null);
    }

    public Serie saveSerie(Serie serie){
        Assert.notNull(serie, "Serie cannot be null");
        return serieDao.save(serie);
    }

    public void deleteSerie(Serie serie){
        Assert.notNull(serie, "serie cannot be null");
        serieDao.delete(serie);
    }
}
