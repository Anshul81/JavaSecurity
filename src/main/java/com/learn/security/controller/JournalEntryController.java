package com.learn.security.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learn.security.entity.JournalEntry;
import com.learn.security.entity.User;
import com.learn.security.service.JournalEntryService;
import com.learn.security.service.UserService;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

	@Autowired
	JournalEntryService journalEntryService;

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<List<JournalEntry>> getJournalEntriesOfAUser() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			Optional<User> user = userService.getUserByUserName(userName);
			User users = user.get();
			List<JournalEntry> entries = users.getJournalEntries();
			if(!entries.isEmpty() && entries!=null ) {
				return new ResponseEntity<List<JournalEntry>>(entries,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping
	public ResponseEntity<JournalEntry> saveJournalEntry(@RequestBody JournalEntry entry) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			journalEntryService.saveJournalEntry(entry, userName);
			return new ResponseEntity<JournalEntry>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("id/{id}")
	public ResponseEntity<String> deleteJournalEntry(@PathVariable ObjectId id) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			journalEntryService.deleteJournalEntry(id, userName);
			return new ResponseEntity<String>("Journal Entry Deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}}

	@GetMapping("id/{id}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Optional<User> optionalUserByUserName = userService.getUserByUserName(userName);
		User userByUserName = optionalUserByUserName.get();
		List<JournalEntry> collect = userByUserName.getJournalEntries().stream().filter(x-> x.getId().equals(id)).collect(Collectors.toList());

		if(!collect.isEmpty()) {
			Optional<JournalEntry> entry = journalEntryService.getJournalEntryById(id);
			if (entry.isPresent()) {
				return new ResponseEntity<>(entry.get(),HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	//TODO
	@PutMapping("id/{id}")
	public ResponseEntity<JournalEntry> updateJournalEntry(@RequestBody JournalEntry entry, @PathVariable ObjectId id) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			journalEntryService.updateJournalEntry(id, entry, userName);
			return new ResponseEntity<JournalEntry>(entry, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
