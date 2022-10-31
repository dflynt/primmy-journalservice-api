package org.dflynt.primmy.journalservice.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
public class JournalPreview {

    @Id
    long id;

    @Column(name="journalid")
    String journalId;

    @Column(name="title")
    String title;

    @Column(name = "createddate")
    Timestamp createdDate;

    @Column(name="lastmodified")
    Timestamp lastmodified;

    public JournalPreview() {}

    public JournalPreview(String journalId, String title,
                          Timestamp createdDate, Timestamp lastModified) {
        this.journalId = journalId;
        this.title = title;
        this.createdDate = createdDate;
        this.lastmodified = lastModified;
    }
}