package org.dflynt.primmy.journalservice.repositories;

import org.dflynt.primmy.journalservice.models.Journal;
import org.dflynt.primmy.journalservice.models.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    @Query(value="SELECT *" +
                " FROM topic t" +
                " WHERE t.userid = :userId", nativeQuery = true)
    List<Topic> findAllByUserId(@Param("userId") String userId);

    @Query(value="SELECT *" +
            "FROM topic t" +
            "WHERE userid = :userId and topicid = :topicId" +
            "", nativeQuery = true)
    Topic findByuserIdandTopicId(@Param("userId") String userId, @Param("topicId") String topicId);
}
