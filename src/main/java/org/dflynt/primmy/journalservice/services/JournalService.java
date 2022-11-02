package org.dflynt.primmy.journalservice.services;

import org.dflynt.primmy.journalservice.models.Figure;
import org.dflynt.primmy.journalservice.models.Journal;
import org.dflynt.primmy.journalservice.models.JournalPreview;
import org.dflynt.primmy.journalservice.models.Topic;
import org.dflynt.primmy.journalservice.repositories.FigureRepository;
import org.dflynt.primmy.journalservice.repositories.JournalPreviewRepository;
import org.dflynt.primmy.journalservice.repositories.JournalRepository;
import org.dflynt.primmy.journalservice.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class JournalService {

    @Autowired
    JournalRepository journalRepository;
    @Autowired
    JournalPreviewRepository journalPreviewRepository;
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    FigureRepository figureRepository;

    @Autowired
    public JournalService(JournalRepository journalRepository, JournalPreviewRepository journalPreviewRepository,
                          TopicRepository topicRepository, FigureRepository figureRepository) {
        this.journalRepository = journalRepository;
        this.journalPreviewRepository = journalPreviewRepository;
        this.topicRepository = topicRepository;
        this.figureRepository = figureRepository;
    }

    public List<Topic> getTopicsByUserId(String userId) {
        List<Topic> topics = this.topicRepository.findAllByUserId(userId);

        return topics;
    }

    public Topic createTopic(Topic topic) {
        String uuid = UUID.randomUUID().toString();
        topic.setTopicId(uuid);

        return topicRepository.save(topic);
    }

    public Topic updateTopic(String topicId, String userId, Map<String, String> changes) {
        Topic topic = getTopicByUserIdAndTopicId(topicId, userId);

        for(String s : changes.keySet()) {
            switch(s) {
                case "topicName":
                    topic.setTopicName(changes.get("topicName"));
                    break;
                case "color":
                    topic.setColor(changes.get("color"));
                    break;
                case "classId":
                    topic.setClassId((changes.get("classId")));
                    break;
            }
        }

        topicRepository.save(topic);

        return topic;
    }

    public Topic getTopicByUserIdAndTopicId(String userId, String topicId) {
        Topic topic = this.topicRepository.findByuserIdandTopicId(userId, topicId);

        return topic;
    }

    public List<JournalPreview> getJournalPreviewsByUserIdAndTopicId(String userId, String topicId) {
        return journalPreviewRepository.findJournalPreviewByUserId(userId, topicId);
    }

    public Journal getJournalById(String journalId) {
        return journalRepository.findByjournalId(journalId);
    }

    public Journal createJournal(Journal journal) {
        String uuid = UUID.randomUUID().toString();
        journal.setJournalId(uuid);

        return journalRepository.save(journal);
    }

    public void updateJournalText(String journalId, String journalText) {
        journalRepository.updateJournalEntryText(journalId, journalText);
    }

    public List<Figure> getFigures(String journalId) {
        return figureRepository.getFiguresByUserId(journalId);
    }
    public boolean uploadFigures(String journalId, MultipartFile[] files) throws IOException {
        String uuid;
        for(MultipartFile file : files) {
            uuid = UUID.randomUUID().toString();
            Figure f = new Figure(journalId, uuid, file.getResource().getFilename(), file.getBytes());
            System.out.println(f.getData().length);
            figureRepository.save(f);
        }

        return true;
    }

    public boolean deleteFigure(String journalId, String figureId) {
        figureRepository.deleteByUserIdAndFigureId(journalId, figureId);
        return true;
    }

    public void deleteSingleJournalByJournalId(String journalId) {
        journalRepository.deleteSingleJournalByJournalId(journalId);
        figureRepository.deleteAllFiguresByJournalId(journalId);
    }

    public void deleteAllJournalsByUserId(String userId) {
        journalRepository.deleteAllJournalsByUserId(userId);
    }
}
