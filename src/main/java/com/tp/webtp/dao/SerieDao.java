package com.tp.webtp.dao;

import com.tp.webtp.entity.Serie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SerieDao extends CrudRepository<Serie, UUID> {
}
