package com.tp.webtp.dao;

import com.tp.webtp.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShareDao extends JpaRepository<Share, UUID> {
    public List<Share> findByUserId(UUID id);
}
