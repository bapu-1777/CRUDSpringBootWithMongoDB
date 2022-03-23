package com.example.demomongodabApi;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends MongoRepository<Student,String> {

    @Query(value =  "{'lastName': ?0", fields = "")
    Optional<Student> findByStudent(String student);

}
