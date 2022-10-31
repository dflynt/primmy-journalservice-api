package org.dflynt.primmy.journalservice.repositories;

import org.dflynt.primmy.journalservice.models.Journal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface JournalRepository extends CrudRepository<Journal, Long> {

    Journal findByjournalId(String journalId);
    List<Journal> findByuserId(String userId);

    @Modifying
    @Query(value = "DELETE FROM Journal WHERE journalid = :journalId", nativeQuery = true)
    void deleteSingleJournalByJournalId(@Param("journalId") String journalId);

    @Modifying
    @Query(value = "DELETE FROM Journal WHERE userid = :userId", nativeQuery = true)
    void deleteAllJournalsByUserId(@Param("userId") String userId);
}