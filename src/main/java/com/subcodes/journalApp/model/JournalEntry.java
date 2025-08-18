package com.subcodes.journalApp.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class JournalEntry {
    @Id
    private ObjectId id;

    private String title;

    private String content;

    private LocalDateTime date;
}
