package com.tp.webtp.dao;

import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventDao extends JpaRepository<Event, UUID> {
     List<Event> findBySerieId(UUID Id);
}
