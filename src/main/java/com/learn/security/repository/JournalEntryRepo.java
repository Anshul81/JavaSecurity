package com.learn.security.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.learn.security.entity.JournalEntry;
@Repository
public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId>{

}
