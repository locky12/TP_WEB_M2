package com.tp.webtp.dao;

import com.tp.webtp.entity.Share;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShareDao extends CrudRepository<Share, UUID> {
}
