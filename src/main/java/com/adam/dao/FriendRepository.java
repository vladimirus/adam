package com.adam.dao;


import com.adam.model.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface FriendRepository extends MongoRepository<Friend, String> {
    Friend findByName(String name);
}
