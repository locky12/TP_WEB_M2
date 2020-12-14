package com.tp.webtp.dao;

import com.tp.webtp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagDao extends JpaRepository<Tag, UUID> {
    public List<Tag> findByEventId(UUID id);
}
