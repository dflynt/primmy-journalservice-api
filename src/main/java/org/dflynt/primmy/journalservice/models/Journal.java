package org.dflynt.primmy.journalservice.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class Journal {

    @Id
    long id;

    @Column(name = "journalid")
    String journalId;

    @Column(name = "userid")
    String userId;

    @Column(name = "title")
    String title;

    @Column(name = "subtitle")
    String subTitle;

    @Column(name = "entry")
    String entry;

    @Column(name = "figures")
    String figures;

    @Column(name = "createddate")
    Timestamp createdDate;

    @Column(name = "lastmodified")
    Timestamp lastModified;

    @Column(name = "hidden")
    boolean hidden;

    @Column(name = "topicid")
    String topicId;

    public Journal() {}

    public Journal(String test) {}

    public Journal(String journalId, String userId, String title, String subTitle, String entry, String figures,
                   Timestamp createdDate, Timestamp lastModified, boolean hidden, String topicId) {
        this.journalId = journalId;
        this.userId = userId;
        this.title = title;
        this.subTitle = subTitle;
        this.entry = entry;
        this.figures = figures;
        this.createdDate = createdDate;
        this.lastModified = lastModified;
        this.hidden = hidden;
        this.topicId = topicId;
    }
}
