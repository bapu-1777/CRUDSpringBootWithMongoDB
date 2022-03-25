package com.example.demomongodabApi;



import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static com.mongodb.client.model.Aggregates.match;

@RestController
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    MongoTemplate mongoTemplate;

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
    public ResponseEntity<?> putStudent(@Valid @RequestBody Student student){

        try{
            student.setTime(new Date(System.currentTimeMillis()));
            studentRepo.save(student);

            return new ResponseEntity<Student>(student,HttpStatus.OK);
        } catch (Exception e){

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

    @GetMapping("/agre")
    public ResponseEntity<?> byAgregetion(){
        String data;
        List<Student> allStudents =studentRepo.findAll();
        if (allStudents.size()>0){

            String uri = "mongodb://localhost:27017";
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("mayank");
            MongoCollection<Document> collection = database.getCollection("Student");
            collection.aggregate(
                    Arrays.asList(
                            match(Filters.eq("lastName","Vekariya"))
//                            Aggregates.group("$spendInBook", Accumulators.sum("count", 1))
                    )
            ).forEach(doc -> System.out.println(doc.toJson()));
            return new ResponseEntity<List<Student>>(allStudents, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("NO Student available",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/agres")
    public ResponseEntity<List<Document>> byAgregetionreturn(){

        List<Student> allStudents =studentRepo.findAll();
        if (allStudents.size()>0){

            String uri = "mongodb://localhost:27017";
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("mayank");
            MongoCollection<Document> collection = database.getCollection("Student");
            Bson match = match(Filters.eq("lastName", "Vekariya"));
            List<Document> results = collection.aggregate(Arrays.asList(match))
                    .into(new ArrayList<>());
//            System.out.println(results);
//            Gson gson = new Gson();
//            String jsonCartList = gson.toJson(results);



            return  ResponseEntity.ok(results);
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/agress")
    public ResponseEntity<List<Student>> byAgregetionreturntemplet(){

        List<Student> allStudents =studentRepo.findAll();
        if (allStudents.size()>0){

            MatchOperation matchStage = Aggregation.match(new Criteria("fristName").is("mayank"));


            Aggregation aggregation
                    = Aggregation.newAggregation(matchStage);

            AggregationResults<Student> output
                    = mongoTemplate.aggregate(aggregation, "Student", Student.class);




            return  ResponseEntity.ok(output.getMappedResults());
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/mongo")
    public ResponseEntity<?> byjs(){


            String uri = "mongodb://localhost:27017";
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("mayank");
//            return database.doEval("addNumbers(3,4)").toString();
        Document doc1 = database.runCommand(new Document("db.loadServerScripts()","addNumbers(4,5)"));
        System.out.println(doc1);
        return null;
    }
}
