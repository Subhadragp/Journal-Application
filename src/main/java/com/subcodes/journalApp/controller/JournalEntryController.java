package com.subcodes.journalApp.controller;

import com.subcodes.journalApp.model.JournalEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/")
    public List<JournalEntry> getJournalEntries() {
        return new ArrayList<JournalEntry>(journalEntries.values());
    }
}
