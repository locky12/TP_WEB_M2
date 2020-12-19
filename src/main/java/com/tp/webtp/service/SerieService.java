package com.tp.webtp.service;

import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.entity.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SerieService {

    @Autowired
    SerieDao serieDao;

    public Serie getSerieBySerieId(UUID idSerie){
        Optional<Serie> serie = serieDao.findById(idSerie);
        return serie.orElse(null);
    }
}
