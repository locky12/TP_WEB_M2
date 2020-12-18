package com.tp.webtp.dao;

import com.tp.webtp.entity.Event;
import com.tp.webtp.entity.Role;
import com.tp.webtp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TagDao extends JpaRepository<Tag, UUID> {

    @Query("select t.event from Tag as t, Share as s where t.tagName=:tagName and t.event.serie.id=s.serie.id and s.user.id=:userId")
    public List<Event> getEventsByTagNameAndUserId(@Param(value = "tagName") String tagName, @Param(value = "userId") UUID userId);

    @Query("select t from Tag as t, Share as s where t.event.id=:eventId and t.event.serie.id=s.serie.id and s.user.id=:userId group by t.tagName")
    public List<Tag> getTagsByUserIdAndEventId(@Param(value = "eventId") UUID eventId, @Param(value = "userId") UUID userId);

    @Query("select t from Tag  as t, Share as s where t.event.serie.id=s.serie.id and s.user.id=:userId")
    public List<Tag> getTagNamesByUserId(@Param(value = "userId") UUID userId);

    @Query("select max(t.event.date) from Tag as t, Share as s where t.tagName=:tagName and t.event.serie.id=s.serie.id and s.user.id=:userId")
    public Date getEventsDateByTagNameAndUserId(@Param(value = "tagName") String tagName, @Param(value = "userId") UUID userId);

    @Query("select count(t) from Tag as t, Share as s where t.tagName=:tagName and t.event.serie.id=s.serie.id and s.user.id=:userId and t.event.date>:debut and t.event.date<:fin")
    public Integer getTagFrequencyBetweenDates(@Param(value = "tagName") String tagName, @Param(value = "userId") UUID userId,
                                               @Param(value = "debut") Date debut, @Param(value = "fin") Date fin);
}
