package com.mitrais.cdc.mongodbapp.repository;

import com.mitrais.cdc.mongodbapp.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
    User findBy_id(ObjectId _id);
    User findByEmail(String email);
}
