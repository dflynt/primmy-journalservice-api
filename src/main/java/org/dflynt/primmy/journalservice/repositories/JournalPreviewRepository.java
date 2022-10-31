package org.dflynt.primmy.journalservice.repositories;

import org.dflynt.primmy.journalservice.models.JournalPreview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalPreviewRepository extends CrudRepository<JournalPreview, Long> {

    @Query(value = "SELECT * " +
            "FROM Journal " +
            "WHERE userid = :userId and topicid = :topicId", nativeQuery = true)
    List<JournalPreview> findJournalPreviewByUserId(@Param("userId") String userId, @Param("topicId") String topicId);
}