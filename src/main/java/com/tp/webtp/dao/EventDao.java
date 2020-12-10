package com.tp.webtp.dao;

import com.tp.webtp.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventDao extends CrudRepository<Event, UUID> {
}
