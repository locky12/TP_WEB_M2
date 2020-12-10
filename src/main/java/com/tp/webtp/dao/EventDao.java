package com.tp.webtp.dao;

import com.tp.webtp.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventDao extends CrudRepository<Event,Long> {
}
