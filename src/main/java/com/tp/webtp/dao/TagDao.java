package com.tp.webtp.dao;

import com.tp.webtp.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TagDao extends CrudRepository<Tag,Long> {

}
