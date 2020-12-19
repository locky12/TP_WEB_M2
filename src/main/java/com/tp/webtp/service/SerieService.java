package com.tp.webtp.service;

import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SerieService {

    @Autowired
    SerieDao serieDao;

    public Serie getSerieBySerieId(UUID idSerie){
        Optional<Serie> serie = serieDao.findById(idSerie);
        return serie.isPresent() ? serie.get() : null;
    }
}
