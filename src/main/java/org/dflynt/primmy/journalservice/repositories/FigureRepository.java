package org.dflynt.primmy.journalservice.repositories;

import org.dflynt.primmy.journalservice.models.Figure;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface FigureRepository extends CrudRepository<Figure, Long> {

    @Query(value = "SELECT * FROM figure f WHERE f.journalId = :journalId ", nativeQuery = true)
    List<Figure> getFiguresByUserId(@Param("journalId") String journalId);

    @Modifying
    @Query(value = "DELETE FROM figure where figureId = :figureId and journalId = :journalId", nativeQuery = true)
    void deleteByUserIdAndFigureId(@Param("journalId") String journalId, @Param("figureId") String figureId);
}
