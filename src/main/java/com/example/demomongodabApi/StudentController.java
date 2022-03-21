package com.example.demomongodabApi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;


import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;

    @GetMapping("/student")
    public ResponseEntity<?> getAllStudent(){
        List<Student> allStudents =studentRepo.findAll();
        if (allStudents.size()>0){
            return new ResponseEntity<List<Student>>(allStudents, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("NO Student available",HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/student")
    public ResponseEntity<?> putStudent(@RequestBody Student student){

        try{
            student.setTime(new Date(System.currentTimeMillis()));
            studentRepo.save(student);
            return new ResponseEntity<Student>(student,HttpStatus.OK);
        }
        catch (Exception e){

            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);


        }


    }

    @GetMapping("/student/{id}")
    public ResponseEntity<?> getSingleStudent(@PathVariable("id") String id){

        Optional<Student> studentOptional=studentRepo.findById(id);
        if (studentOptional.isPresent()){
            return new ResponseEntity<>(studentOptional.get(),HttpStatus.OK);

        }
        else {

            return new ResponseEntity<>("Student not found with "+id , HttpStatus.NOT_FOUND);

        }
    }
    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateSingleStudent(@PathVariable("id") String id,@RequestBody Student student){

        Optional<Student> studentOptional=studentRepo.findById(id);
        if (studentOptional.isPresent()){
            Student studentsave = studentOptional.get();
            studentsave.setFristName(student.getFristName() != null ? student.getFristName() : studentsave.getFristName());
            studentsave.setLastName(student.getLastName() != null ? student.getLastName() : studentsave.getLastName());
            studentsave.setEmail(student.getEmail() != null ? student.getEmail() : studentsave.getEmail());
            studentsave.setGender(student.getGender() != null ? student.getGender() : studentsave.getGender());
            studentsave.setAddress(student.getAddress() != null ? student.getAddress() : studentsave.getAddress());
            studentsave.setSubjects(student.getSubjects() != null ? student.getSubjects() : studentsave.getSubjects());
            studentsave.setSpendInBook(student.getSpendInBook() != null ? student.getSpendInBook() : studentsave.getSpendInBook());
            studentsave.setTime(new Date(System.currentTimeMillis()));
            studentRepo.save(studentsave);
            return new ResponseEntity<>(studentsave,HttpStatus.OK);

        }
        else {

            return new ResponseEntity<>("Student not found with "+id , HttpStatus.NOT_FOUND);

        }
    }
    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try{
            studentRepo.deleteById(id);
            return new ResponseEntity<>("student "+id+" deleted",HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}
