package com.subcodes.journalApp.scheduler;

import com.subcodes.journalApp.cache.AppCache;
import com.subcodes.journalApp.model.JournalEntry;
import com.subcodes.journalApp.model.User;
import com.subcodes.journalApp.repository.UserRepositoryImpl;
import com.subcodes.journalApp.service.EmailService;
import com.subcodes.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache appCache;

    //    @Scheduled(cron = "0 0 9 * * SUN")
    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void fetchUsersAndSendSentimentAnalysisMail() {
        List<User> users = userRepository.getUsersForSentimAnalysis();
        for (User user : users) {
            List<JournalEntry> jEntries = user.getJournalEntries();
            List<String> filteredLists = jEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getContent()).collect(Collectors.toList());
            String entries = String.join("%", filteredLists);
            String sentiment = sentimentAnalysisService.getSentiment(entries);
            emailService.sendEmail(user.getEmail(), "Sentiment Analysis for last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 0/10 * 1/1 * ?")
    public void clearAppCache() {
        appCache.init();
    }
}
