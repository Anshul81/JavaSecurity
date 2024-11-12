package com.learn.security.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.learn.security.entity.User;


@Repository
public interface UserRepo extends MongoRepository<User, ObjectId>{
	
	 Optional<User> findByUserName(String userName);
	 void deleteByUserName (String userName);
}
