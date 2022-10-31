package org.dflynt.primmy.journalservice.controllers;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.coyote.Response;
import org.dflynt.primmy.journalservice.exceptions.InvalidTokenException;
import org.dflynt.primmy.journalservice.exceptions.TokenExpirationException;
import org.dflynt.primmy.journalservice.models.Figure;
import org.dflynt.primmy.journalservice.models.Journal;
import org.dflynt.primmy.journalservice.models.JournalPreview;
import org.dflynt.primmy.journalservice.models.Topic;
import org.dflynt.primmy.journalservice.services.AuthService;
import org.dflynt.primmy.journalservice.services.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class JournalController {
    private final String ACCESS_TOKEN = "37f3d42fd20eb72055aea46bbc31f00c5b49ca480b3c613a5288602f6d4af7a5";
    private final String REFRESH_TOKEN = "6d0e34ec58f18b8b18d952cc6a3d084410665a2ae382769faebf80680aba9215";
    byte[] accessKeyBytes;
    byte[] refreshKeyBytes;
    Key accessKey;
    Key refreshKey;

    @Autowired
    JournalService journalService;

    @Autowired
    AuthService authservice;

    private static final Logger logger = LoggerFactory.getLogger(JournalController.class);

    public JournalController(JournalService service){
        journalService = service;
        accessKeyBytes = Decoders.BASE64.decode(ACCESS_TOKEN);
        refreshKeyBytes = Decoders.BASE64.decode(REFRESH_TOKEN);

        accessKey = Keys.hmacShaKeyFor(accessKeyBytes);
        refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    @GetMapping(value="/journal/{journalId}")
    public ResponseEntity getJournal(@RequestHeader("Authorization") String authHeader, @PathVariable String journalId) throws Exception {
        Journal journal = journalService.getJournalById(journalId);

        return new ResponseEntity<Journal>(journal, HttpStatus.OK);
    }

    @GetMapping(value="/journal/preview/user/{userId}/topic/{topicId}")
    public ResponseEntity<List<JournalPreview>> getJournalPreviews(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String userId, @PathVariable String topicId) throws Exception{
        List<JournalPreview> previews = journalService.getJournalPreviewsByUserIdAndTopicId(userId, topicId);

        return new ResponseEntity<>(previews, HttpStatus.OK);
    }

    @GetMapping(value="/topics/user/{userId}")
    public ResponseEntity<List<Topic>> getTopics(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String userId) throws Exception{
        List<Topic> topics = journalService.getTopicsByUserId(userId);

        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PostMapping(value="/topics/createTopic")
    public ResponseEntity createTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody Topic topic) throws Exception {
        Topic t = this.journalService.createTopic(topic);

        if(t == null) {
            logger.warn("Unable to create topic for " + topic.getUserId());
            return new ResponseEntity<String>("Topic creation failed", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<Topic>(t, HttpStatus.OK);
        }
    }

    @PatchMapping(value="/journal/topic/{topicId}/user/{userId}")
    public ResponseEntity updateTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String topicId,
                                      @PathVariable String userId, @RequestBody Map<String, String> updates) throws Exception{
        Topic topic = this.journalService.updateTopic(topicId, userId, updates);
        logger.info("UPDATE topic {}", topic);

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping(value="/journal/createJournal")
    public ResponseEntity createJournal(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody Journal journal) throws Exception{
        Journal j = journalService.createJournal(journal);

        if(j == null) {
            logger.warn("Unable to create topic for " + journal.getUserId());
            return new ResponseEntity<String>("Topic creation failed", HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<Journal>(j, HttpStatus.OK);
        }
    }

    @PatchMapping("/journal/{journalId}")
    public ResponseEntity patchJournal(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String journalId, @RequestBody Map<String, String> changes) throws Exception {
        journalService.updateJournal(journalId, changes);
        logger.info("UPDATE journal {}", journalId);
        return null;
    }

    @DeleteMapping(value="/journal/{journalId}")
    public ResponseEntity deleteJournalById(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String journalId) throws Exception{

        journalService.deleteSingleJournalByJournalId(journalId);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @DeleteMapping(value="/journal/user/{userId}")
    public ResponseEntity deleteAllJournalsByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String userId) throws Exception{
        journalService.deleteAllJournalsByUserId(userId);
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

    @GetMapping("/journal/{journalId}/figures")
    public ResponseEntity getFigures(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String journalId) throws Exception {
        List<Figure> figures = journalService.getFigures(journalId);

        return new ResponseEntity(figures, HttpStatus.OK);
    }

    @PostMapping(value="/journal/{journalId}/uploadFigures")
    public ResponseEntity uploadFigures(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestParam("files") MultipartFile[] files, @PathVariable String journalId) throws IOException {
        journalService.uploadFigures(journalId, files);

        return new ResponseEntity(true, HttpStatus.OK);
    }

    @DeleteMapping(value="/journal/{journalId}/figure/{figureId}")
    public ResponseEntity deleteFigure(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @PathVariable String journalId, @PathVariable String figureId) throws IOException {
        System.out.println("JournalId: " + journalId);
        System.out.println("FigureId: " + figureId);
        journalService.deleteFigure(journalId, figureId);
        return new ResponseEntity(true, HttpStatus.OK);
    }

}