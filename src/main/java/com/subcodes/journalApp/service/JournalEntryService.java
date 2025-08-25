package com.subcodes.journalApp.service;

import com.subcodes.journalApp.model.JournalEntry;
import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    public List<JournalEntry> getUserJournalEntries(String username) {
        User user = userService.getUserByUserName(username);
        return user.getJournalEntries();
    }

    @Transactional
    public void createJournalEntry(JournalEntry journalEntry, String username) {
        try{
            User userFromDb = userService.getUserByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            userFromDb.getJournalEntries().add(saved);
            userService.saveUser(userFromDb);
        } catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while saving the journal entry", e);
        }
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteJournalEntryById(ObjectId myId, String username) {
        boolean removed = false;
        try {
            User userFromDb = userService.getUserByUserName(username);
            removed = userFromDb.getJournalEntries().removeIf(j -> j.getId().equals(myId));
            if (removed) {
                userService.saveUser(userFromDb);
                journalEntryRepository.deleteById(myId);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while deleting the journal entry", e);
        }
        return removed;
    }

    public Optional<JournalEntry> updateJournalEntryById(ObjectId id, JournalEntry newEntry) {
        return getJournalEntryById(id).map(old -> {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            return journalEntryRepository.save(old);
        });
    }
}
