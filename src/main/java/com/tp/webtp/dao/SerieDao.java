package com.tp.webtp.dao;

import com.tp.webtp.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SerieDao extends JpaRepository<Serie, UUID> {
}
