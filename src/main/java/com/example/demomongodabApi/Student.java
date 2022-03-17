package com.example.demomongodabApi;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "Student")
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private String id;
    private String fristName;
    private String lastName;
    private String email;
    private String gender;
    private Address address;
    private List<String> subjects;
    private BigDecimal spendInBook;
    private Date time;



}
