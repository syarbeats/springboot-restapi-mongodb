package com.mitrais.cdc.mongodbapp.repository;

import com.mitrais.cdc.mongodbapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
