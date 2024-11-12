package com.learn.security.entity;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "journal_entry")
public class JournalEntry {
	
	@Id
	private ObjectId id;
	@NonNull
	private String title;
	
	private String content;
	
	private LocalDateTime date;
}
