package org.dflynt.primmy.journalservice.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Topic {
    
    @Id
    long id;        
            
    @Column(name="topicid")
    String topicId;

    @Column(name="userid")
    String userId;

    @Column(name="topicname")
    String topicName;

    @Column(name="color")
    String color;

    @Column(name="classid")
    String classId;

    @Column(name="createddate")
    Date createdDate;

    public Topic() { }

    public Topic(String userId, String classId, String topicId, String topicName, String color, Date createdDate) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.color = color;
        this.classId = classId;
        this.userId = userId;
        this.createdDate = createdDate;
    }
}
