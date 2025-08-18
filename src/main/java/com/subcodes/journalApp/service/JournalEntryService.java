package com.subcodes.journalApp.service;

import com.subcodes.journalApp.model.JournalEntry;
import com.subcodes.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public void createJournalEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId);
    }

    public void deleteJournalEntryById(ObjectId myId) {
        journalEntryRepository.deleteById(myId);
    }

    public Optional<JournalEntry> updateJournalEntryById(ObjectId id, JournalEntry newEntry) {
        return getJournalEntryById(id).map(old -> {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            return journalEntryRepository.save(old);
        });
    }
}
