package com.mitrais.cdc.mongodbapp.repository;

import com.mitrais.cdc.mongodbapp.model.Employee;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends MongoRepository<Employee, String>{

    Employee findBy_id(ObjectId _id);

}
