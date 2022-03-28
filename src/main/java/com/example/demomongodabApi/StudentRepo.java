package com.example.demomongodabApi;

import org.bson.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends MongoRepository<Student,String> {

    @Query(value =  "{'lastName': ?0}")
    List<Student> findByStudent(String name);


    @Query("use mayank")
    void usemauyank();

    @Query("db.loadServerScripts()")
    void loadserver();

    @Query("addNumbers(7,3)")
    Document findBYFunction();


}
