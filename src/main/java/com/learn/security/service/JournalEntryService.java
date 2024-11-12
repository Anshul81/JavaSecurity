package com.learn.security.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.security.entity.JournalEntry;
import com.learn.security.entity.User;
import com.learn.security.repository.JournalEntryRepo;
import com.learn.security.repository.UserRepo;

@Service
public class JournalEntryService {
	
	@Autowired
	JournalEntryRepo journalEntryRepo;
	
	@Autowired
	UserService userService;
	
	@Transactional
	public void saveJournalEntry(JournalEntry entry,String userName) {
		Optional<User> optionalUser = userService.getUserByUserName(userName);
		entry.setDate(LocalDateTime.now());
		JournalEntry entries = journalEntryRepo.save(entry);
		if (optionalUser.isPresent()){
			User user = optionalUser.get();
			user.getJournalEntries().add(entries);
			userService.saveUser(user);
		}
	}
	
	@Transactional
	public void deleteJournalEntry(ObjectId id, String userName) {
		Optional<User> optionalUser = userService.getUserByUserName(userName);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.getJournalEntries().removeIf(x -> x.getId().equals(id));
			userService.saveUser(user);
			journalEntryRepo.deleteById(id);
		}
	}
	
	public List<JournalEntry> getJournalEntries() {
		List<JournalEntry> entries = journalEntryRepo.findAll();
		return entries;
	}
	//This has a issue - If I give diff user in AUTH it will still update
	public boolean updateJournalEntry(ObjectId id, JournalEntry newEntry,String userName) {
		Optional<User> optionalUser = userService.getUserByUserName(userName);
		if (optionalUser.isPresent()){
			List<JournalEntry> collect = optionalUser.get().getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
			if (!collect.isEmpty()){
				Optional<JournalEntry> oldEntry = journalEntryRepo.findById(id);
				if (oldEntry.isPresent()) {
					JournalEntry updatedEntry = oldEntry.get();
					updatedEntry.setTitle(newEntry.getTitle());
					updatedEntry.setContent(newEntry.getContent());
					journalEntryRepo.save(updatedEntry);
					return true;
				}
			}

		}
		return false;
	}
	
	public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
		Optional<JournalEntry> entry = journalEntryRepo.findById(id);
		return entry;
	}
}
