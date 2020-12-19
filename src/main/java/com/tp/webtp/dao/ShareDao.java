package com.tp.webtp.dao;

import com.tp.webtp.entity.Role;
import com.tp.webtp.entity.Serie;
import com.tp.webtp.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShareDao extends JpaRepository<Share, UUID> {

    public List<Share> findByUserId(UUID id);

    @Query("select s.serie from Share as s where s.user.id=:userId")
    public List<Serie> getSeriesByUserId(@Param(value = "userId") UUID userId);

    @Query("select s.serie from Share as s where s.user.id=:userId and s.role=:role")
    public List<Serie> getSeriesByUserIdAndRole(@Param(value = "userId") UUID userId, @Param(value = "role") Role role);

    @Query("select s.serie from Share as s where s.user.id=:userId and s.role<>:role")
    public List<Serie> getSeriesByUserIdAndNotRole(@Param(value = "userId") UUID userId, @Param(value = "role") Role role);

    @Query("select user from Share")
    public List<Share> getAll();

    @Query("select s.serie from Share as s where s.user.id=:userId and s.serie.id=:serieId")
    public Optional<Serie> getFromUserIdAndSerieId(@Param(value = "userId") UUID userId, @Param(value = "serieId") UUID serieId);

    @Query("select s from Share as s where s.user.id=:userId and s.serie.id=:serieId and s.role<>:role")
    public Optional<Share> getFromUserIdAndSerieIdAndNotRole(@Param(value = "userId") UUID userId, @Param(value = "serieId") UUID serieId, @Param(value = "role") Role role);

    @Query("select s from Share as s where s.user.id=:userId and s.serie.id=:serieId and s.role=:role")
    public Optional<Share> getFromUserIdAndSerieIdAndRole(@Param(value = "userId") UUID userId, @Param(value = "serieId") UUID serieId, @Param(value = "role") Role role);

}
