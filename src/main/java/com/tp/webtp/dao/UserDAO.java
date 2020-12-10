package com.tp.webtp.dao;

import com.tp.webtp.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDAO extends CrudRepository<User, UUID> {
}
