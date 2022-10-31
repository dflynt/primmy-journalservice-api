package org.dflynt.primmy.journalservice.models;

import javax.persistence.*;
import javax.transaction.Transactional;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@Entity
@Transactional
public class Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "journalid")
    String journalId;

    @Column(name = "figureid")
    String figureId;
    @Column(name = "filename")
    String fileName;

    @Lob
    byte[] data;

    @Column(name="lastmodified")
    Date createdDate;

    public Figure() {
    }

    public Figure(String journalId, String figureId, String fileName, byte[] data) {
        this.figureId = figureId;
        this.journalId = journalId;
        this.fileName = fileName;
        this.data = data;
        this.createdDate = new Date();
    }
}
