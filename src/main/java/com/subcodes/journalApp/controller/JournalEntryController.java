package com.subcodes.journalApp.controller;

import com.subcodes.journalApp.model.JournalEntry;
import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.service.JournalEntryService;
import com.subcodes.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getUserJournalEntries(@PathVariable String username) {
        return new ResponseEntity<>(journalEntryService.getUserJournalEntries(username), HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
        try{
            journalEntryService.createJournalEntry(journalEntry, username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.getJournalEntryById(myId)
                .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String username) {
        journalEntryService.deleteJournalEntryById(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        return journalEntryService.updateJournalEntryById(id, newEntry)
                .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
