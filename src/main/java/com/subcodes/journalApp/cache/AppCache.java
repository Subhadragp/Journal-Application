package com.subcodes.journalApp.cache;

import com.subcodes.journalApp.model.ConfigJournalApp;
import com.subcodes.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init() {
        List<ConfigJournalApp> allApis = configJournalAppRepository.findAll();
        for (ConfigJournalApp api : allApis) {
            APP_CACHE.put(api.getKey(), api.getValue());
        }
    }

}
