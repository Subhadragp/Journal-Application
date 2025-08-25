package com.subcodes.journalApp.controller;

import com.subcodes.journalApp.model.JournalEntry;
import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.service.JournalEntryService;
import com.subcodes.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<JournalEntry>> getUserJournalEntries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ResponseEntity<>(journalEntryService.getUserJournalEntries(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            journalEntryService.createJournalEntry(journalEntry, username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUserName(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(myId)).toList();
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(myId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteJournalEntryById(myId, username);
        if (removed) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUserName(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).toList();
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(id);
            if (journalEntry.isPresent()) {
                return journalEntryService.updateJournalEntryById(id, newEntry)
                        .map(updated -> new ResponseEntity<>(updated, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
